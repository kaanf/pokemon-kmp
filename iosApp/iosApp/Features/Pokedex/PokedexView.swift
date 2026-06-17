import KMPObservableViewModelSwiftUI
import SharedLogic
import SwiftUI

struct PokedexView: View {

    @StateViewModel private var viewModel = SharedDI.pokedexViewModel()

    @State private var toast: ToastContent?
    @State private var showTeamFullSheet = false

    private static let portraitColumns = 2
    private static let landscapeColumns = 5

    private static let prefetchRowsAhead = 2
    private static let initialShimmerRows = 4

    private func gridColumns(_ count: Int) -> [GridItem] {
        Array(repeating: GridItem(.flexible(), spacing: AppTheme.Spacing.sm), count: count)
    }

    var body: some View {
        GeometryReader { proxy in
            let columnCount = proxy.size.width > proxy.size.height
                ? Self.landscapeColumns
                : Self.portraitColumns

            content(columnCount: columnCount)
                .background(AppTheme.Colors.background)
                .overlay(alignment: .bottom) {
                    if let toast {
                        ToastBanner(
                            message: toast.message,
                            iconName: toast.iconName,
                            actionTitle: toast.retry == nil ? nil : "pokedex.retry",
                            onAction: toast.retry
                        )
                        .padding(.bottom, AppTheme.Spacing.xl)
                        .transition(.move(edge: .bottom).combined(with: .opacity))
                    }
                }
        }
        .adaptiveModal(
            isPresented: $showTeamFullSheet,
            sheet: { TeamFullSheet(onDismiss: { showTeamFullSheet = false }) },
            dialog: {
                AppDialog(onDismiss: { showTeamFullSheet = false }) { dismiss in
                    TeamFullContent(isCompact: true, onClose: dismiss)
                }
            }
        )
        .collect(viewModel.events, onEach: handle(event:))
    }

    // MARK: - Sections

    @ViewBuilder
    private func content(columnCount: Int) -> some View {
        let state = viewModel.state

        let showShimmer = (state.isInitialLoading || (state.pokemon?.isEmpty ?? false)) && state.error == nil && !state.endReached

        ZStack {
            if showShimmer {
                shimmerGrid(columnCount: columnCount)
                    .transition(.opacity)
            } else if (state.pokemon?.isEmpty ?? true), let error = state.error {
                ErrorStateView(
                    error: error,
                    onRetry: { viewModel.onAction(action: PokedexActionOnRetry.shared) }
                )
            } else {
                grid(state: state, columnCount: columnCount)
                    .transition(.opacity)
            }
        }
        .animation(.easeInOut(duration: 0.3), value: showShimmer)
    }

    private func grid(state: PokedexState, columnCount: Int) -> some View {
        let teamIds = Set(state.teamMemberIds.map(\.int32Value))
        let loadedPokemon = state.pokemon ?? []
        let columns = gridColumns(columnCount)
        let prefetchDistance = columnCount * Self.prefetchRowsAhead
        let compact = columnCount > Self.portraitColumns

        return ScrollView {
            LazyVGrid(columns: columns, spacing: AppTheme.Spacing.sm) {
                ForEach(Array(loadedPokemon.enumerated()), id: \.element.id) { index, pokemon in
                    NavigationLink(value: pokemon.id) {
                        PokemonGridCard(
                            pokemon: pokemon,
                            isInTeam: teamIds.contains(pokemon.id),
                            onAddToTeam: { viewModel.onAction(action: PokedexActionOnAddToTeam(pokemon: pokemon)) },
                            compact: compact
                        )
                    }
                    .buttonStyle(.plain)
                    .onAppear {
                        if index >= loadedPokemon.count - prefetchDistance {
                            viewModel.onAction(action: PokedexActionOnLoadMore.shared)
                        }
                    }
                }
            }
            .padding(AppTheme.Spacing.sm)

            if state.isLoadingMore {
                LazyVGrid(columns: columns, spacing: AppTheme.Spacing.sm) {
                    ForEach(0..<columnCount, id: \.self) { _ in
                        PokemonGridCardShimmer()
                    }
                }
                .padding(.horizontal, AppTheme.Spacing.sm)
                .padding(.bottom, AppTheme.Spacing.sm)
                .transition(.opacity)
            }
        }
        .animation(.easeInOut(duration: 0.25), value: state.isLoadingMore)
    }

    private func shimmerGrid(columnCount: Int) -> some View {
        ScrollView {
            LazyVGrid(columns: gridColumns(columnCount), spacing: AppTheme.Spacing.sm) {
                ForEach(0..<(columnCount * Self.initialShimmerRows), id: \.self) { _ in
                    PokemonGridCardShimmer()
                }
            }
            .padding(AppTheme.Spacing.sm)
        }
        .scrollDisabled(true)
    }

    // MARK: - Events

    private func handle(event: PokedexEvent) {
        switch event {
        case is PokedexEventTeamFull:
            showTeamFullSheet = true
        case is PokedexEventAddedToTeam:
            show(String(localized: "pokedex.added_to_team"), iconName: "ic_pokeball")
        case is PokedexEventRemovedFromTeam:
            show(String(localized: "pokedex.removed_from_team"), iconName: "ic_pokeball")
        case is PokedexEventAddToTeamFailed:
            show(String(localized: "pokedex.add_failed"), iconName: "ic_pokeball")
        case is PokedexEventRemoveFromTeamFailed:
            show(String(localized: "pokedex.remove_failed"), iconName: "ic_pokeball")
        case is PokedexEventShowPagingError:
            show(
                String(localized: "pokedex.paging_error"),
                iconName: "ic_pokeball",
                retry: { viewModel.onAction(action: PokedexActionOnRetry.shared) },
                seconds: 5
            )
        default:
            break
        }
    }

    private func show(_ message: String, iconName: String? = nil, retry: (() -> Void)? = nil, seconds: Double = 2) {
        let content = ToastContent(message: message, iconName: iconName, retry: retry)
        withAnimation { toast = content }
        Task {
            try? await Task.sleep(for: .seconds(seconds))
            withAnimation {
                if toast?.id == content.id { toast = nil }
            }
        }
    }
}

private struct ToastContent: Identifiable {
    let id = UUID()
    let message: String
    var iconName: String?
    var retry: (() -> Void)?
}
