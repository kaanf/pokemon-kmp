import SharedLogic
import SwiftUI

struct DetailBaseStatsCard: View {

    let detail: DetailUiModel

    private var accent: Color {
        (detail.types.first ?? PokemonType.unknown).colors.solid
    }

    private var stats: [PokemonStat] {
        detail.stats.filter { $0.type != StatType.other }
    }

    var body: some View {
        DetailSectionCard {
            VStack(alignment: .leading, spacing: AppTheme.Spacing.md) {
                Text("detail.base_stats")
                    .appFont(AppTheme.Typography.titleLarge)
                    .foregroundStyle(AppTheme.Colors.contentSecondary)

                VStack(spacing: AppTheme.Spacing.sm) {
                    ForEach(stats, id: \.type.ordinal) { stat in
                        statRow(stat: stat)
                    }
                }
            }
            .padding(AppTheme.Spacing.lg)
        }
    }

    private func statRow(stat: PokemonStat) -> some View {
        HStack(spacing: 0) {
            Text(stat.type.shortLabel())
                .appFont(AppTheme.Typography.titleSmall)
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .frame(width: 56, alignment: .leading)

            GeometryReader { geometry in
                ZStack(alignment: .leading) {
                    RoundedRectangle(cornerRadius: 4)
                        .fill(accent.opacity(0.2))
                    RoundedRectangle(cornerRadius: 4)
                        .fill(accent)
                        .frame(width: geometry.size.width * barFraction(of: stat))
                }
            }
            .frame(height: 8)

            Text(String(stat.value))
                .appFont(AppTheme.Typography.titleSmall)
                .foregroundStyle(AppTheme.Colors.contentSecondary)
                .frame(width: 48, alignment: .trailing)
        }
    }

    private func barFraction(of stat: PokemonStat) -> CGFloat {
        let fraction = CGFloat(stat.value) / CGFloat(PokemonStat.companion.MAX_BASE_STAT)
        return min(max(fraction, 0), 1)
    }
}
