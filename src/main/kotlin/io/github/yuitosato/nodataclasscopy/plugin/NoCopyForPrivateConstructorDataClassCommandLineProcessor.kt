package io.github.yuitosato.nodataclasscopy.plugin

import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

const val PluginId = "io.github.yuitosato.nodataclasscopy.plugin"

val KEY_TAG = CompilerConfigurationKey<String>("Tags to use for logging")
val OPTION_TAG = CliOption(
    optionName = "tag",
    valueDescription = "String",
    description = KEY_TAG.toString(),
)

// @AutoService(CommandLineProcessor::class)
@OptIn(ExperimentalCompilerApi::class)
class NoCopyForPrivateConstructorDataClassCommandLineProcessor : CommandLineProcessor {
    override val pluginId = PluginId

    override val pluginOptions = listOf(OPTION_TAG)

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration,
    ) {
        when (val optionName = option.optionName) {
            OPTION_TAG.optionName -> configuration.put(KEY_TAG, value)
            else -> error("Unknown plugin option: $optionName")
        }
    }
}
