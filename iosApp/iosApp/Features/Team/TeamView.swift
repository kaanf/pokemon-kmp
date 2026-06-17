import KMPObservableViewModelSwiftUI
import SharedLogic
import SwiftUI

struct TeamView: View {

    @StateViewModel private var viewModel = SharedDI.teamViewModel()

    @State private var toast: ToastItem?

    var body: some View {
        content
            .background(AppTheme.Colors.background)
            .overlay(alignment: .bottom) {
                if let toast {
                    ToastBanner(message: toast.message, iconName: "ic_pokeball")
                        .padding(.bottom, AppTheme.Spacing.xl)
                        .transition(.move(edge: .bottom).combined(with: .opacity))
                }
            }
            .collect(viewModel.events, onEach: handle(event:))
    }

    // MARK: - Sections

    @ViewBuilder
    private var content: some View {
        let members = viewModel.state.members
        let isTeamFull = viewModel.state.emptySlots == 0

        if members.isEmpty {
            // No team yet — center the prompt on screen.
            AddMorePokemonCard()
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .padding(AppTheme.Spacing.lg)
        } else {
            // Landscape (width > height) shows two columns; portrait stays single.
            GeometryReader { geometry in
                let columnCount = geometry.size.width > geometry.size.height ? 2 : 1
                let columns = Array(
                    repeating: GridItem(.flexible(), spacing: AppTheme.Spacing.sm),
                    count: columnCount
                )

                ScrollView {
                    VStack(spacing: 0) {
                        LazyVGrid(columns: columns, spacing: AppTheme.Spacing.sm) {
                            ForEach(members, id: \.pokemon.id) { member in
                                NavigationLink(value: member.pokemon.id) {
                                    PokemonTeamCard(
                                        member: member,
                                        onRemove: {
                                            viewModel.onAction(
                                                action: TeamActionOnRemoveMember(pokemonId: member.pokemon.id)
                                            )
                                        }
                                    )
                                }
                                .buttonStyle(.plain)
                            }
                        }

                        if !isTeamFull {
                            AddMorePokemonCard()
                                .padding(.top, AppTheme.Spacing.sm)
                        }
                    }
                    .padding(AppTheme.Spacing.sm)
                }
            }
        }
    }

    // MARK: - Events

    private func handle(event: TeamEvent) {
        switch event {
        case is TeamEventRemovedFromTeam:
            show(String(localized: "pokedex.removed_from_team"))
        case is TeamEventRemoveFailed:
            show(String(localized: "pokedex.remove_failed"))
        default:
            break
        }
    }

    private func show(_ message: String, seconds: Double = 2) {
        let item = ToastItem(message: message)
        withAnimation { toast = item }
        Task {
            try? await Task.sleep(for: .seconds(seconds))
            withAnimation {
                if toast?.id == item.id { toast = nil }
            }
        }
    }
}

private struct ToastItem: Identifiable {
    let id = UUID()
    let message: String
}

private struct AddMorePokemonCard: View {
    var body: some View {
        VStack(spacing: AppTheme.Spacing.sm) {
            AddPokemonGlyph()
                .frame(width: 160, height: 160)

            Text("team.add_more")
                .appFont(AppTheme.Typography.headlineLarge)
                .foregroundStyle(AppTheme.Colors.contentSecondary)
                .multilineTextAlignment(.center)
                .fixedSize(horizontal: false, vertical: true)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, AppTheme.Spacing.lg)
    }
}
