import SwiftUI

struct PokemonGridCardShimmer: View {

    var body: some View {
        VStack(alignment: .leading, spacing: AppTheme.Spacing.sm) {
            RoundedCornerShape(cornerRadius: AppTheme.Radius.card)
                .fill(AppTheme.Colors.shimmerBase)
                .aspectRatio(1, contentMode: .fit)
                .frame(maxWidth: .infinity)
                .shimmer()

            RoundedCornerShape(cornerRadius: 4)
                .fill(AppTheme.Colors.shimmerBase)
                .frame(width: 96, height: 18)
                .shimmer()

            RoundedCornerShape(cornerRadius: 4)
                .fill(AppTheme.Colors.shimmerBase)
                .frame(width: 56, height: 14)
                .shimmer()
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
