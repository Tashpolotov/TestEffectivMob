pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TestEffectivMob"
include(":app")
include(":registration:domain")
include(":registration:data")
include(":registration:presenter")
include(":core_utils")
include(":main:domain")
include(":main:data")
include(":main:presenter")
include(":catalog:data")
include(":catalog:domain")
include(":catalog:presenter")
include(":basket:data")
include(":basket:domain")
include(":basket:presenter")
include(":profile:data")
include(":profile:domain")
include(":profile:presenter")
include(":sale:data")
include(":sale:domain")
include(":sale:presenter")
include(":viewmodel:viewmodel")
