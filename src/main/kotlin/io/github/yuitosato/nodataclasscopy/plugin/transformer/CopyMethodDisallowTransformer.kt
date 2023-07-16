package io.github.yuitosato.nodataclasscopy.plugin.transformer

import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.util.constructors
import org.jetbrains.kotlin.ir.util.parentAsClass

class CopyMethodDisallowTransformer(
    pluginContext: IrPluginContext,
) : IrElementTransformerVoidWithContext() {

    override fun visitCall(expression: IrCall): IrExpression {
        expression.transformChildrenVoid()

        val ownerClass = expression.symbol.owner.parentAsClass
        if (expression.symbol.owner.name.asString() == "copy" &&
            ownerClass.isData &&
            ownerClass.constructors.any { it.visibility == DescriptorVisibilities.PRIVATE }
        ) {
            throw RuntimeException("Usage of copy method is not allowed for data classes with private constructors.")
        }

        return expression
    }
}
