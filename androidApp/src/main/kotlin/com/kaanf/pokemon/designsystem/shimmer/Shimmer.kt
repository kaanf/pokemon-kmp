package com.kaanf.pokemon.designsystem.shimmer

import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.platform.InspectorInfo
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private const val SWEEP_PERIOD_NANOS = 1_200_000_000L

fun Modifier.shimmerEffect(
    baseColor: Color,
    highlightColor: Color,
    shape: Shape = RectangleShape,
): Modifier = this then ShimmerElement(baseColor, highlightColor, shape)

private data class ShimmerElement(
    val baseColor: Color,
    val highlightColor: Color,
    val shape: Shape,
) : ModifierNodeElement<ShimmerNode>() {
    override fun create(): ShimmerNode = ShimmerNode(baseColor, highlightColor, shape)

    override fun update(node: ShimmerNode) {
        node.baseColor = baseColor
        node.highlightColor = highlightColor
        node.shape = shape
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "shimmerEffect"
        properties["baseColor"] = baseColor
        properties["highlightColor"] = highlightColor
        properties["shape"] = shape
    }
}

private class ShimmerNode(
    var baseColor: Color,
    var highlightColor: Color,
    var shape: Shape,
) : Modifier.Node(), DrawModifierNode {
    private var progress = 0f

    override fun onAttach() {
        coroutineScope.launch {
            while (isActive) {
                withFrameNanos { frameTimeNanos ->
                    progress = (frameTimeNanos % SWEEP_PERIOD_NANOS) /
                        SWEEP_PERIOD_NANOS.toFloat()
                }
                invalidateDraw()
            }
        }
    }

    override fun ContentDrawScope.draw() {
        val startOffsetX = (4f * progress - 2f) * size.width
        val brush = Brush.linearGradient(
            colors = listOf(baseColor, highlightColor, baseColor),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width, size.height),
        )

        if (shape === RectangleShape) {
            drawRect(brush)
        } else {
            drawOutline(shape.createOutline(size, layoutDirection, this), brush)
        }
        drawContent()
    }
}
