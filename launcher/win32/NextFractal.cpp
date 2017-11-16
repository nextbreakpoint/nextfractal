#include <windows.h>
#include <jni.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>
#include <stdexcept>
#include <iostream>

struct start_args {
    JavaVMInitArgs vm_args;
    char *launch_class;

    start_args(const char **args, const char *classname)
    {
        vm_args.options = 0;
        vm_args.nOptions = 0;
        launch_class = 0;

        int arg_count = 0;
        const char **atarg = args;
        while (*atarg++) arg_count++;

        JavaVMOption *options = new JavaVMOption[arg_count];
        vm_args.nOptions = arg_count;
        for (int i = 0; i < vm_args.nOptions; i++)
            options[i].optionString = 0;
        vm_args.options = options;

        while (*args) {
            options->optionString = strdup(*args);
            printf("%s\n", options->optionString);
            options++;
            args++;
        }
        vm_args.version = JNI_VERSION_1_6;
        vm_args.ignoreUnrecognized = JNI_TRUE;
        launch_class = strdup(classname);
    }

    ~start_args() {
        if (launch_class)
            free(launch_class);
        for (int i = 0; i < vm_args.nOptions; i++)
            if (vm_args.options[i].optionString)
                free(vm_args.options[i].optionString);
        if (vm_args.options)
            delete[] vm_args.options;
    }

    start_args(const start_args &rhs) {
        vm_args.options = 0;
        launch_class = 0;

        vm_args.options = new JavaVMOption[rhs.vm_args.nOptions];
        vm_args.nOptions = rhs.vm_args.nOptions;
        for (int i = 0; i < vm_args.nOptions; i++) {
            vm_args.options[i].optionString = strdup(rhs.vm_args.options[i].optionString);
        }
        vm_args.version = rhs.vm_args.version;
        vm_args.ignoreUnrecognized = rhs.vm_args.ignoreUnrecognized;
        launch_class = strdup(rhs.launch_class);
    }

    start_args &operator=(const start_args &rhs) {
        for (int i = 0; i < vm_args.nOptions; i++) {
            if (vm_args.options[i].optionString) free(vm_args.options[i].optionString);
        }
        delete[] vm_args.options;

        vm_args.options = new JavaVMOption[rhs.vm_args.nOptions];
        vm_args.nOptions = rhs.vm_args.nOptions;
        for (int i = 0; i < vm_args.nOptions; i++)
            vm_args.options[i].optionString = 0;
        for (int i = 0; i < vm_args.nOptions; i++)
            vm_args.options[i].optionString = strdup(rhs.vm_args.options[i].optionString);
        vm_args.version = rhs.vm_args.version;
        vm_args.ignoreUnrecognized = rhs.vm_args.ignoreUnrecognized;
        if (launch_class) free(launch_class);
        launch_class = strdup(rhs.launch_class);
        return *this;
    }
};

void ShowAlert(const std::string message, const std::runtime_error& error) {
    std::string alertMessage = std::string(message).append("\n\nCause: ").append(error.what());
    MessageBox(NULL, (LPCSTR)alertMessage.c_str(), (LPCSTR)"Oops something is wrong...", MB_ICONERROR | MB_OK | MB_DEFBUTTON2);
}

typedef int (JNICALL * JNICreateJavaVM)(JavaVM** jvm, JNIEnv** env, JavaVMInitArgs* initargs);

void * start_java(void *start_args) {
    struct start_args *args = (struct start_args *)start_args;

    DWORD retval;
    HKEY jKey;
    if (retval = RegOpenKeyEx(HKEY_LOCAL_MACHINE, TEXT("SOFTWARE\\JavaSoft\\Java Development Kit"), 0, KEY_READ, &jKey)) {
        RegCloseKey(jKey);
        throw std::runtime_error("Registry key not found");
    }

    TCHAR versionString[16]; // version numbers shouldn't be longer than 16 chars
    DWORD bufsize = 16 * sizeof(TCHAR);
    if (retval = RegGetValue(jKey, NULL, TEXT("CurrentVersion"), RRF_RT_REG_SZ, NULL, versionString, &bufsize)) {
        RegCloseKey(jKey);
        throw std::runtime_error("Key value not found: CurrentVersion");
    }

    TCHAR pathString[512];
    bufsize = 512 * sizeof(TCHAR);
    if (retval = RegGetValue(jKey, versionString, TEXT("JavaHome"), RRF_RT_REG_SZ, NULL, pathString, &bufsize)) {
        RegCloseKey(jKey);
        throw std::runtime_error("Key value not found: JavaHome");
    }

    std::string path = std::string(pathString);
    std::cout << "Found java \"" << path << "\"" << std::endl;

    RegCloseKey(jKey);

    std::string libPath = path + "/jre/bin/server/jvm.dll";
    std::cout << "Use library \"" << libPath << "\"" << std::endl;

    HMODULE jniModule = LoadLibrary(libPath.c_str());
    if (NULL == jniModule) {
        throw std::runtime_error("Failed to open library");
    }

    JNICreateJavaVM createJavaVM = (JNICreateJavaVM)GetProcAddress(jniModule, "JNI_CreateJavaVM");
    if (NULL == createJavaVM) {
        FreeLibrary(jniModule);
        throw std::runtime_error("Function JNI_CreateJavaVM not found");
    }

    int res;
    JavaVM *jvm;
    JNIEnv *env;

    res = createJavaVM(&jvm, &env, &args->vm_args);
    if (res != JNI_OK) {
        FreeLibrary(jniModule);
        throw std::runtime_error("Failed to create jvm");
    }
    /* load the launch class */
    jclass main_class;
    jmethodID main_method_id;
    main_class = env->FindClass(args->launch_class);
    if (main_class == NULL) {
        printf("Cannot find class\n");
        jvm->DestroyJavaVM();
        FreeLibrary(jniModule);
        throw std::runtime_error("Main class not found");
    }
    /* get main method */
    main_method_id = env->GetStaticMethodID(main_class, "main", "([Ljava/lang/String;)V");
    if (main_method_id == NULL) {
        printf("Cannot find method\n");
        jvm->DestroyJavaVM();
        FreeLibrary(jniModule);
        throw std::runtime_error("Method main not found");
    }
    /* make the initial argument */
    jobject empty_args = env->NewObjectArray(0, env->FindClass("java/lang/String"), NULL);
    /* call the method */
    env->CallStaticVoidMethod(main_class, main_method_id, empty_args);
    /* Don't forget to destroy the JVM at the end */
    jvm->DestroyJavaVM();

    FreeLibrary(jniModule);

    return (0);
}

std::string GetClasspath(std::string path) {
   std::string s = std::string();
   DIR* dirFile = opendir(path.c_str());
   if (dirFile) {
      struct dirent* hFile;
      while ((hFile = readdir(dirFile)) != NULL) {
         if (!strcmp(hFile->d_name, "." )) continue;
         if (!strcmp(hFile->d_name, "..")) continue;
         if (hFile->d_name[0] == '.') continue;
         if (strstr(hFile->d_name, ".jar")) {
            s.append(path);
            s.append("/");
            s.append(hFile->d_name);
            s.append(";");
         }
      }
      s.append(".");
      closedir(dirFile);
   }
   return s;
}

std::string GetExePath() {
  char buffer[MAX_PATH];
  GetModuleFileName(NULL, buffer, MAX_PATH);
  return std::string(buffer);
}

std::string GetBasePath(std::string exePath) {
    return exePath.substr(0, exePath.find_last_of("\\"));
}

int APIENTRY WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPTSTR lpCmdLine, int nCmdShow) {
    try {
        FreeConsole();
        std::string memMaxArg = std::string();
        char * varMemMax = getenv("NEXTFRACTAL_MAX_MEMORY");
        int varMemMaxLen = varMemMax != NULL ? strlen(varMemMax) : 0;
        if (varMemMaxLen > 0) {
            memMaxArg.append("-Xmx");
            memMaxArg.append(std::to_string(std::stoi(varMemMax)));
            memMaxArg.append("g");
        } else {
            memMaxArg.append("-Xmx3g");
        }
        std::string basePath = GetBasePath(GetExePath());
        std::cout << "Base path " << basePath << std::endl;
        std::string jarsPath = basePath + "/resources";
        std::string classpathArg = "-Djava.class.path=" + GetClasspath(jarsPath);
        std::string libPathArg = "-Djava.library.path=" + basePath + "/resources";
        std::string locPathArg = "-Dbrowser.location=" + basePath + "/examples";
        const char *vm_arglist[] = {
            "-Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.LogConfig",
            classpathArg.c_str(),
            libPathArg.c_str(),
            locPathArg.c_str(),
            memMaxArg.c_str(),
            0
        };
        struct start_args args(vm_arglist, "com/nextbreakpoint/nextfractal/runtime/javafx/NextFractalApp");
        start_java((void *)&args);
    } catch (const std::runtime_error& e) {
        ShowAlert("Did you install Java JDK 8 or later?", e);
        exit(-1);
    }
    return 0;
}
