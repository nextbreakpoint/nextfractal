#include <Foundation/Foundation.h>
#include <mach-o/dyld.h>
#include <jni.h>
#include <dlfcn.h>
#include <dirent.h>
#include <stdexcept>
#include <iostream>

typedef int (JNICALL * JLILaunch)(
        int argc, char ** argv,              /* main argc, argc */
        int jargc, const char** jargv,          /* java args */
        int appclassc, const char** appclassv,  /* app classpath */
        const char* fullversion,                /* full version defined */
        const char* dotversion,                 /* dot version defined */
        const char* pname,                      /* program name */
        const char* lname,                      /* launcher name */
        jboolean javaargs,                      /* JAVA_ARGS */
        jboolean cpwildcard,                    /* classpath wildcard */
        jboolean javaw,                         /* windows-only javaw */
        jint     ergo_class                     /* ergnomics policy */
);

static void ShowAlert(const std::string message, const std::runtime_error& error) {
    std::string alertMessage = std::string(message).append("\n\nCause: ").append(error.what());
    CFStringRef cfTitle = CFStringCreateWithCString(NULL, "Oops something is wrong...", kCFStringEncodingUTF8);
    CFStringRef cfMessage = CFStringCreateWithCString(NULL, alertMessage.c_str(), kCFStringEncodingUTF8);
    CFUserNotificationDisplayNotice(0, kCFUserNotificationStopAlertLevel, NULL, NULL, NULL, cfTitle, cfMessage, NULL);
    CFRelease(cfTitle);
    CFRelease(cfMessage);
}

static std::string GetExePath() {
    return std::string([[[[NSBundle mainBundle] executablePath] stringByResolvingSymlinksInPath] UTF8String]);
}

static std::string GetBasePath(std::string exePath) {
    return exePath.substr(0, exePath.find_last_of("/"));
}

int main(int argc, char **argv) {
    try {
        std::string basePath = GetBasePath(GetExePath());
        std::cout << "Base path " << basePath << std::endl;

        std::string libraryPathArg = "-Djava.library.path=" + basePath + "/../../libs";
        std::string locationArg = "-Dbrowser.location=" + basePath + "/../../../../../examples";
        std::string loggingArg = "-Djava.util.logging.config.class=com.nextbreakpoint.nextfractal.runtime.logging.LogConfig";
        std::string modulePathArg = basePath + "/../../jars";
        std::string mainClassArg = "com.nextbreakpoint.nextfractal.runtime.javafx.NextFractalApp";

        std::string libPath = basePath + "/../lib/jli/libjli.dylib";
        std::cout << "Lib path " << libPath << std::endl;

        void* lib_handle = dlopen(libPath.c_str(), RTLD_LOCAL | RTLD_LAZY);
        if (!lib_handle) {
            std::string message = std::string("Cannot open library ").append(libPath);
            throw std::runtime_error(message);
        }

        JLILaunch launch = (JLILaunch)dlsym(lib_handle, "JLI_Launch");
        if (!launch) {
            dlclose(lib_handle);
            throw std::runtime_error("Function JLI_Launch not found");
        }

        std::string memMaxArg = std::string();
        char * varMemMax = getenv("NEXTFRACTAL_MAX_MEMORY");
        int varMemMaxLen = varMemMax != NULL ? strlen(varMemMax) : 0;
        if (varMemMaxLen > 0) {
            memMaxArg.append("-Xmx");
            memMaxArg.append(std::to_string(std::stoi(varMemMax)));
            memMaxArg.append("m");
        } else {
            memMaxArg.append("-Xmx4g");
        }

        int jargc = 9;
        const char *jargv[] = {
            "--module-path",
            modulePathArg.c_str(),
            "--add-modules",
            "ALL-MODULE-PATH",
            loggingArg.c_str(),
            libraryPathArg.c_str(),
            locationArg.c_str(),
            memMaxArg.c_str(),
            mainClassArg.c_str()
        };

        for (int i = 0; i < jargc; i++) {
            std::cout << jargv[i] << std::endl;
        }

        launch(argc, argv, jargc, jargv, 0, NULL, "2.0.3", "0.0", argv[0], argv[0], jargc > 0, JNI_FALSE, JNI_FALSE, 0);
    } catch (const std::runtime_error& e) {
        ShowAlert("Some error occurred while launching the application", e);
        exit(-1);
    }

    return 0;
}
