import KMPObservableViewModelSwiftUI
import SharedLogic
import SwiftUI

struct HomeView: View {
    @StateViewModel private var homeViewModel = SharedDI.homeViewModel()

    @State private var selectedTab: AppTab = .pokedex
    @State private var path: [Int32] = []
    @State private var showSettings = false

    var body: some View {
        NavigationStack(path: $path) {
            VStack(spacing: 0) {
                AppTopBar(state: topBarState, onSettings: { showSettings = true })

                TabView(selection: $selectedTab) {
                    PokedexView()
                        .tag(AppTab.pokedex)
                        .tabItem {
                            Label(
                                AppTab.pokedex.titleKey,
                                image: selectedTab == .pokedex
                                    ? "ic_pokeball_filled_bottom_tab"
                                    : "ic_pokeball_bottom_tab"
                            )
                        }

                    TeamView()
                        .tag(AppTab.team)
                        .tabItem {
                            Label(
                                AppTab.team.titleKey,
                                image: selectedTab == .team ? "ic_shield_filled_bottom_tab" : "ic_shield_bottom_tab"
                            )
                        }
                }
                .ignoresSafeArea(.container, edges: .bottom)
            }
            .background(AppTheme.Colors.background.ignoresSafeArea(edges: [.top, .horizontal]))
            .toolbar(.hidden, for: .navigationBar)
            .settingsModal(isPresented: $showSettings)
            .navigationDestination(for: Int32.self) { pokemonId in
                DetailView(pokemonId: pokemonId)
            }
        }
    }

    private var topBarState: AppTopBarState {
        switch selectedTab {
        case .pokedex:
            AppTopBarStateList.shared
        case .team:
            AppTopBarStateTeam(teamCount: homeViewModel.teamCount)
        }
    }
}
