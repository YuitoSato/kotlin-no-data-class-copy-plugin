# kotlin-no-data-class-copy-plugin

This plugin is currently under development.

This Kotlin compiler plugin aims to restrict the use of the copy function in data classes with private constructors.
In Kotlin, a data class generates a copy function automatically. However, sometimes you may want to restrict the usage of this function, for  example, when the class has a private constructor.

This plugin will issue a compiler error whenever it detects the usage of the copy function in a data class with a private constructor.
