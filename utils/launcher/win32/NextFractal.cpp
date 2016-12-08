#include <windows.h>
#include <jni.h>
#include <stdlib.h>
#include <dirent.h>
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

void * start_java(void *start_args) {
    struct start_args *args = (struct start_args *)start_args;
    int res;
    JavaVM *jvm;
    JNIEnv *env;

    res = JNI_CreateJavaVM(&jvm, (void**)&env, &args->vm_args);
    if (res < 0) exit(1);
    /* load the launch class */
    jclass main_class;
    jmethodID main_method_id;
    main_class = env->FindClass(args->launch_class);
    if (main_class == NULL) {
        printf("Cannot find class\n");
        jvm->DestroyJavaVM();
        exit(1);
    }
    /* get main method */
    main_method_id = env->GetStaticMethodID(main_class, "main", "([Ljava/lang/String;)V");
    if (main_method_id == NULL) {
        printf("Cannot find method\n");
        jvm->DestroyJavaVM();
        exit(1);

    }
    /* make the initial argument */
    jobject empty_args = env->NewObjectArray(0, env->FindClass("java/lang/String"), NULL);
    /* call the method */
    env->CallStaticVoidMethod(main_class, main_method_id, empty_args);
    /* Don't forget to destroy the JVM at the end */
    jvm->DestroyJavaVM();
    return (0);
}

std::string getClasspath(const char* path) {
   std::string s = std::string();
   DIR* dirFile = opendir(path);
   if (dirFile) {
      struct dirent* hFile;
      errno = 0;
      while ((hFile = readdir(dirFile)) != NULL) {
         if (!strcmp(hFile->d_name, "." )) continue;
         if (!strcmp(hFile->d_name, "..")) continue;

         // in linux hidden files all start with '.'
         if (hFile->d_name[0] == '.') continue;

         if (strstr(hFile->d_name, ".jar")) {
            //printf("%s\n", hFile->d_name);
            s.append(path);
            s.append("/");
            s.append(hFile->d_name);
            s.append(":");
         }
      }
      s.append(".");
      closedir(dirFile);
   }
   return s;
}

std::string GetExeFileName() {
  char buffer[MAX_PATH];
  GetModuleFileName( NULL, buffer, MAX_PATH );
  return std::string(buffer);
}

std::string GetExePath() {
  std::string f = GetExeFileName();
  return f.substr(0, f.find_last_of( "\\/" ));
}

int main(int argc, char **argv) {
    std::string base = GetExePath();
    printf("Base directory %s\n", base.c_str());
    std::string jarPath = base + "/NextFractal";
    std::string classpathArg = "-Djava.class.path=" + getClasspath(jarPath.c_str());
    std::string libPathArg = "-Djava.library.path=" + base + "/NextFractal";
    std::string locPathArg = "-Dbrowser.location=" + base + "/examples";
    const char *vm_arglist[] = {
        "-Xmx1g",
        "-Djava.util.logging.config=com.nextbreakpoint.nextfractal.runtime.LogConfig",
        classpathArg.c_str(),
        libPathArg.c_str(),
        locPathArg.c_str(),
        0
    };
    struct start_args args(vm_arglist, "com/nextbreakpoint/nextfractal/runtime/javaFX/NextFractalApp");
    start_java(args);
}
