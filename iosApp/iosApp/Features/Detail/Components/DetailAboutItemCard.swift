import SwiftUI

struct DetailAboutItemCard: View {
    let label: LocalizedStringKey
    let imageName: String
    let value: String
    let typeColors: PokemonTypeColors

    var body: some View {
        DetailSectionCard {
            VStack(alignment: .leading, spacing: AppTheme.Spacing.md) {
                HStack(alignment: .top) {
                    Text(label)
                        .appFont(AppTheme.Typography.titleLarge)
                        .foregroundStyle(AppTheme.Colors.contentSecondary)

                    Spacer(minLength: AppTheme.Spacing.sm)

                    RoundedTypeIcon(
                        imageName: imageName,
                        tint: typeColors.onContainer,
                        background: typeColors.container
                    )
                }

                Text(value)
                    .appFont(AppTheme.Typography.headlineLarge)
                    .foregroundStyle(AppTheme.Colors.contentPrimary)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(AppTheme.Spacing.lg)
        }
    }
}
