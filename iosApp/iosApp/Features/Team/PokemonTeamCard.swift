import SharedLogic
import SwiftUI

struct PokemonTeamCard: View {

    let member: TeamMember
    let onRemove: () -> Void

    var body: some View {
        HStack(spacing: AppTheme.Spacing.md) {
            artwork

            VStack(alignment: .leading, spacing: AppTheme.Spacing.xs) {
                Text(StringExtKt.toFormattedName(member.pokemon.name))
                    .appFont(AppTheme.Typography.headlineMedium)
                    .foregroundStyle(AppTheme.Colors.contentPrimary)
                    .lineLimit(1)

                HStack(spacing: AppTheme.Spacing.xs) {
                    ForEach(member.types, id: \.ordinal) { type in
                        PokemonTypeChip(type: type)
                    }
                }

                Spacer(minLength: 0)

                Text(addedText)
                    .appFont(AppTheme.Typography.bodyMedium)
                    .foregroundStyle(AppTheme.Colors.contentSecondary)
            }
            .frame(maxWidth: .infinity, alignment: .leading)

            removeButton
        }
        .padding(AppTheme.Spacing.md)
        .frame(height: 130)
        .background(AppTheme.Colors.surface)
        .clipShape(RoundedCornerShape(cornerRadius: 24))
        .overlay(
            RoundedCornerShape(cornerRadius: 24)
                .stroke(AppTheme.Colors.surfaceStroke, lineWidth: 1)
        )
    }

    private var artwork: some View {
        PokemonArtwork(url: member.pokemon.imageUrl)
            .padding(AppTheme.Spacing.sm)
            .aspectRatio(1, contentMode: .fit)
            .frame(maxHeight: .infinity)
            .background(AppTheme.Colors.background)
            .clipShape(RoundedCornerShape(cornerRadius: AppTheme.Radius.card))
    }

    private var removeButton: some View {
        Button(action: onRemove) {
            Image("ic_close")
                .renderingMode(.template)
                .resizable()
                .scaledToFit()
                .frame(width: 14, height: 14)
                .foregroundStyle(AppTheme.Colors.surface)
                .padding(6)
                .background(AppTheme.Colors.contentPrimary, in: Circle())
        }
        .buttonStyle(.plain)
        .accessibilityLabel(Text("team.remove"))
    }

    private var addedText: String {
        let date = Date(timeIntervalSince1970: Double(member.addedAtEpochMillis) / 1000)
        return String(localized: "team.added \(Self.addedDateFormatter.string(from: date))")
    }

    private static let addedDateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMM d, yyyy"
        return formatter
    }()
}
