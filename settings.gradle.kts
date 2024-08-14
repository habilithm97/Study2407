pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Study2407"
include(":app")
include(":glide")
include(":location")
include(":brickbreaker")
include(":recyclerview")
include(":retrofit")
include(":rxjava")
include(":workmanager")
include(":navigation")
include(":koin")
include(":paging")
