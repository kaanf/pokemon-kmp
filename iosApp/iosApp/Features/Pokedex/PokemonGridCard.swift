import SharedLogic
import SwiftUI

struct PokemonGridCard: View {

    let pokemon: Pokemon
    let isInTeam: Bool
    let onAddToTeam: () -> Void
    var compact: Bool = false

    var body: some View {
        VStack(alignment: .leading, spacing: AppTheme.Spacing.xs) {
            ZStack(alignment: .topLeading) {
                AppTheme.Colors.background
                    .frame(maxWidth: .infinity)
                    .aspectRatio(1, contentMode: .fit)
                    .overlay {
                        PokemonArtwork(url: pokemon.imageUrl)
                            .padding(AppTheme.Spacing.lg)
                    }
                    .clipShape(RoundedCornerShape(cornerRadius: AppTheme.Radius.card))

                Button(action: onAddToTeam) {
                    Image("ic_pokeball")
                        .renderingMode(.template)
                        .resizable()
                        .scaledToFit()
                        .foregroundStyle(isInTeam ? AppTheme.Colors.pokeball : AppTheme.Colors.contentSecondary)
                        .frame(width: 28, height: 28)
                        .padding(AppTheme.Spacing.sm)
                }
                .buttonStyle(.plain)
                .accessibilityLabel(Text("pokedex.add_to_team"))
            }

            Text(StringExtKt.toFormattedName(pokemon.name))
                .appFont(compact ? AppTheme.Typography.headlineSmall : AppTheme.Typography.headlineMedium)
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .lineLimit(1)

            Text(StringExtKt.formatPokedexNumber(id: pokemon.id))
                .appFont(compact ? AppTheme.Typography.monoSmall : AppTheme.Typography.monoMedium)
                .foregroundStyle(AppTheme.Colors.contentSecondary)
        }
        .padding(AppTheme.Spacing.md)
        .frame(maxWidth: .infinity)
        .background(AppTheme.Colors.surface)
        .clipShape(RoundedCornerShape(cornerRadius: 24))
        .overlay(
            RoundedCornerShape(cornerRadius: 24)
                .stroke(AppTheme.Colors.surfaceStroke, lineWidth: 1)
        )
    }
}
