import SharedLogic
import SwiftUI

struct PokemonTypeChip: View {

    let type: PokemonType

    var body: some View {
        let colors = type.colors
        Text(StringExtKt.toFormattedName(type.name.lowercased()))
            .appFont(AppTheme.Typography.monoMedium)
            .foregroundStyle(colors.onContainer)
            .padding(.horizontal, AppTheme.Spacing.sm)
            .padding(.vertical, 2)
            .background(colors.container)
            .clipShape(Capsule())
    }
}
