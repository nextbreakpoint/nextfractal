#include <CoreFoundation/CoreFoundation.h>
#include <mach-o/dyld.h>
#include <jni.h>
#include <dlfcn.h>
#include <dirent.h>
#include <stdexcept>
#include <iostream>
#include <regex>

struct start_args {
    JavaVMInitArgs vm_args;

    start_args() {
    }

    start_args(const char **args) {
        vm_args.options = 0;
        vm_args.nOptions = 0;

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
        vm_args.version = JNI_VERSION_10;
        vm_args.ignoreUnrecognized = JNI_TRUE;
    }

    ~start_args() {
        for (int i = 0; i < vm_args.nOptions; i++)
            if (vm_args.options[i].optionString)
                free(vm_args.options[i].optionString);
        if (vm_args.options)
            delete[] vm_args.options;
    }

    start_args(const start_args &rhs) {
        vm_args.options = 0;

        vm_args.options = new JavaVMOption[rhs.vm_args.nOptions];
        vm_args.nOptions = rhs.vm_args.nOptions;
        for (int i = 0; i < vm_args.nOptions; i++) {
            vm_args.options[i].optionString = strdup(rhs.vm_args.options[i].optionString);
        }
        vm_args.version = rhs.vm_args.version;
        vm_args.ignoreUnrecognized = rhs.vm_args.ignoreUnrecognized;
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
        return *this;
    }
};

struct launch_args {
  struct start_args *java_args;
  char *launch_class;
  char *java_home;

  launch_args() {
    launch_class = NULL;
    java_home = NULL;
    java_args = NULL;
  }

  launch_args(const char *javahome, const char *classname, const char ** vm_arglist) {
      launch_class = strdup(classname);
      java_home = javahome != NULL ? strdup(javahome) : NULL;
      java_args = new start_args(vm_arglist);
  }

  ~launch_args() {
      if (launch_class)
          free(launch_class);
      if (java_home)
          free(java_home);
      if (java_args)
          free(java_args);
  }
};

typedef int (JNICALL * JNICreateJavaVM)(JavaVM** jvm, JNIEnv** env, JavaVMInitArgs* initargs);

void launch_java(JNICreateJavaVM createJavaVM, const char *launch_class, struct start_args *args) {
    JavaVM *jvm;
    JNIEnv *env;

    for (int i = 0; i < args->vm_args.nOptions; i++) {
        std::cout << args->vm_args.options[i].optionString << std::endl;
    }

    int res = createJavaVM(&jvm, &env, &args->vm_args);
    if (res < 0) {
        throw std::runtime_error("Cannot create JVM");
    }

    jclass main_class = env->FindClass(launch_class);
    if (main_class == NULL) {
        jvm->DestroyJavaVM();

        throw std::runtime_error("Main class not found");
    }

    jmethodID main_method_id = env->GetStaticMethodID(main_class, "main", "([Ljava/lang/String;)V");
    if (main_method_id == NULL) {
        jvm->DestroyJavaVM();

        throw std::runtime_error("Method main not found");
    }

    jobject empty_args = env->NewObjectArray(0, env->FindClass("java/lang/String"), NULL);
    if (empty_args == NULL) {
      jvm->DestroyJavaVM();

      throw std::runtime_error("Cannot allocate arguments");
    }

    env->CallStaticVoidMethod(main_class, main_method_id, empty_args);

    jvm->DestroyJavaVM();
}

void ShowAlert(const std::string message, const std::runtime_error& error) {
    std::string alertMessage = std::string(message).append("\n\nCause: ").append(error.what());
    CFStringRef cfTitle = CFStringCreateWithCString(NULL, "Oops something is wrong...", kCFStringEncodingUTF8);
    CFStringRef cfMessage = CFStringCreateWithCString(NULL, alertMessage.c_str(), kCFStringEncodingUTF8);
    CFUserNotificationDisplayNotice(0, kCFUserNotificationStopAlertLevel, NULL, NULL, NULL, cfTitle, cfMessage, NULL);
    CFRelease(cfTitle);
    CFRelease(cfMessage);
}

std::string ExecuteCommand(const char* cmd) {
    char buffer[128];
    std::string result = "";
    std::shared_ptr<FILE> pipe(popen(cmd, "r"), pclose);
    if (!pipe) throw std::runtime_error("Failed to open pipe");
    while (!feof(pipe.get())) {
        if (fgets(buffer, 128, pipe.get()) != NULL)
            result += buffer;
    }
    return result;
}

void run_java(struct launch_args *run_args) {
    std::string path;

    if (run_args->java_home != NULL) {
      path.append(run_args->java_home);
      path.erase(remove(path.begin(), path.end(), '\"'), path.end());
      std::cout << "Java Home \"" << path << "\"" << std::endl;
    } else {
      path = ExecuteCommand("/usr/libexec/java_home");
      path.erase(std::remove(path.begin(), path.end(), '\n'), path.end());
      std::cout << "Found java \"" << path << "\"" << std::endl;
    }

    struct start_args *args = NULL;
    std::string libPath = "";

    std::regex path_regex("(jdk1.[0-9]+\\.[0-9]+_[0-9]+\\.jdk)|(jdk-[0-9]+(\\.[0-9]+\\.[0-9]+)?\\.jdk)", std::regex_constants::ECMAScript | std::regex_constants::icase);
    if (std::regex_search(path, path_regex)) {
        std::sregex_iterator version_begin = std::sregex_iterator(path.begin(), path.end(), path_regex);
        std::sregex_iterator version_end = std::sregex_iterator();
        for (std::sregex_iterator i = version_begin; i != version_end; ++i) {
            std::smatch match = *i;
            std::string match_str = match.str();
            if (match_str.find("jdk-") == 0) {
              std::cout << "Found Java SDK: " << match_str << "\n";
              args = run_args->java_args;
              libPath = path + "/lib/server/libjvm.dylib";
              break;
            }
        }
    }

    if (args == NULL) {
        throw std::runtime_error("Cannot detect Java SDK");
    }

    std::cout << "Use library \"" << libPath << "\"" << std::endl;

    void* lib_handle = dlopen(libPath.c_str(), RTLD_LOCAL | RTLD_LAZY);
    if (!lib_handle) {
        std::string message = std::string("Cannot open library ").append(libPath);
        throw std::runtime_error(message);
    }

    JNICreateJavaVM createJavaVM = (JNICreateJavaVM)dlsym(lib_handle, "JNI_CreateJavaVM");
    if (!createJavaVM) {
        dlclose(lib_handle);

        throw std::runtime_error("Function JNI_CreateJavaVM not found");
    }

    try {
        launch_java(createJavaVM, run_args->launch_class, args);

        dlclose(lib_handle);
    } catch (const std::runtime_error& e) {
        dlclose(lib_handle);

        throw e;
    }
}

void * start_java(void *start_args) {
    std::cout << "Launching application..." << std::endl;

    struct launch_args *args = (struct launch_args *)start_args;

    try {
        run_java(args);
    } catch (const std::runtime_error& e) {
      ShowAlert("Some error occurred while launching Java VM. Please install Java JDK version 11 or later. See instruction on https://nextbreakpoint.com/nextfractal.html if you need help.", e);

      exit(-1);
    }

    std::cout << "Terminating application..." << std::endl;

    return NULL;
}

std::string GetClasspath(std::string path) {
   std::string s = std::string();
   std::cout << "Creating classpath..." << std::endl;
   DIR* dirFile = opendir(path.c_str());
   if (dirFile) {
      struct dirent* hFile;
      std::cout << "Scanning folder " << path << "..." << std::endl;
      while ((hFile = readdir(dirFile)) != NULL) {
         if (!strcmp(hFile->d_name, "." )) continue;
         if (!strcmp(hFile->d_name, "..")) continue;
         if (hFile->d_name[0] == '.') continue;
         if (strstr(hFile->d_name, ".jar")) {
            std::cout << "Found jar " << hFile->d_name << std::endl;
            s.append(path);
            s.append("/");
            s.append(hFile->d_name);
            s.append(":");
         }
      }
      closedir(dirFile);
   }
   s.append(".");
   return s;
}

std::string GetExePath() {
    char* result = (char *)malloc(PATH_MAX + 1);
    uint32_t size = PATH_MAX;
    if (_NSGetExecutablePath(result, &size) < 0) {
        throw std::runtime_error("Unable to get executable path");
    }
    return std::string(result, (size > 0) ? size : 0);
}

std::string GetBasePath(std::string exePath) {
    return exePath.substr(0, exePath.find_last_of("/"));
}

int main(int argc, char **argv) {
    try {
        std::string memMaxArg = std::string();
        char * varJavaHome = getenv("NEXTFRACTAL_JAVA_HOME");
        char * varMemMax = getenv("NEXTFRACTAL_MAX_MEMORY");
        int varMemMaxLen = varMemMax != NULL ? strlen(varMemMax) : 0;
        if (varMemMaxLen > 0) {
            memMaxArg.append("-Xmx");
            memMaxArg.append(std::to_string(std::stoi(varMemMax)));
            memMaxArg.append("m");
        } else {
            memMaxArg.append("-Xmx4g");
        }
        std::string basePath = GetBasePath(GetExePath());
        std::cout << "Base path " << basePath << std::endl;
        std::string jarsPath = basePath + "/../Resources";
        std::string classpathArg = "-Djava.class.path=" + GetClasspath(jarsPath);
        std::string libPathArg = "-Djava.library.path=" + basePath + "/../Resources";
        std::string locPathArg = "-Dbrowser.location=" + basePath + "/../../../examples";
        const char *vm_arglist[] = {
            "--add-modules",
            "javafx.controls",
            "--add-addopens",
            "javafx.graphics/javafx.scene.text=ALL-UNNAMED",
            "--add-addopens",
            "javafx.graphics/com.sun.javafx.text=ALL-UNNAMED",
            "--add-addopens",
            "javafx.graphics/com.sun.javafx.geom=ALL-UNNAMED",
            "--add-addopens",
            "javafx.graphics/com.sun.javafx.scene.text=ALL-UNNAMED",
            "-Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.LogConfig",
            classpathArg.c_str(),
            libPathArg.c_str(),
            locPathArg.c_str(),
            memMaxArg.c_str(),
            0
        };
        struct launch_args args(varJavaHome, "com/nextbreakpoint/nextfractal/runtime/javafx/NextFractalApp", vm_arglist);
        pthread_t thr;
        pthread_create(&thr, NULL, start_java, &args);
        CFRunLoopRun();
    } catch (const std::runtime_error& e) {
        ShowAlert("Some error occurred while launching the application", e);
        exit(-1);
    }
    return 0;
}
