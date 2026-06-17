import KMPObservableViewModelSwiftUI
import SharedLogic
import SwiftUI

struct DetailView: View {

    @StateViewModel private var viewModel: DetailViewModel
    @Environment(\.dismiss) private var dismiss
    @Environment(\.verticalSizeClass) private var verticalSizeClass

    @State private var showSettings = false

    private var topBarHeight: CGFloat {
        verticalSizeClass.isCompact ? 52 : 68
    }
    private static let bodyCornerRadius: CGFloat = 28

    init(pokemonId: Int32) {
        _viewModel = StateViewModel(wrappedValue: SharedDI.detailViewModel(pokemonId: Int(pokemonId)))
    }

    var body: some View {
        GeometryReader { proxy in
            ZStack(alignment: .top) {
                content(
                    topInset: proxy.safeAreaInsets.top,
                    isWide: proxy.size.width > proxy.size.height
                )
                header
            }
        }
        .background(AppTheme.Colors.background)
        .navigationBarBackButtonHidden(true)
        .toolbar(.hidden, for: .navigationBar)
        .settingsModal(isPresented: $showSettings)
    }

    // MARK: - Sections

    private var header: some View {
        AppTopBar(
            state: AppTopBarStateDetail.shared,
            onBack: { dismiss() },
            onSettings: { showSettings = true }
        )
    }

    @ViewBuilder
    private func content(topInset: CGFloat, isWide: Bool) -> some View {
        let state = viewModel.state

        if let detail = state.detail {
            detailContent(detail: detail, topInset: topInset, isWide: isWide)
        } else if state.isLoading {
            ProgressView()
                .frame(maxWidth: .infinity, maxHeight: .infinity)
        } else if let error = state.error {
            ErrorStateView(
                error: error,
                onRetry: { viewModel.onAction(action: DetailActionOnRetry.shared) }
            )
        } else {
            Color.clear
                .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
    }

    @ViewBuilder
    private func detailContent(detail: DetailUiModel, topInset: CGFloat, isWide: Bool) -> some View {
        let headerColor = (detail.types.first ?? PokemonType.unknown).colors.solid

        if isWide {
            sideBySideContent(detail: detail, headerColor: headerColor, topInset: topInset)
        } else {
            stackedContent(detail: detail, headerColor: headerColor, topInset: topInset)
        }
    }

    private func stackedContent(detail: DetailUiModel, headerColor: Color, topInset: CGFloat) -> some View {
        ScrollView {
            VStack(spacing: 0) {
                DetailArtwork(
                    imageURL: detail.imageUrl,
                    shinyImageURL: detail.shinyImageUrl,
                    backgroundColor: headerColor,
                    topPadding: topInset + topBarHeight
                )

                detailBody(detail: detail)
                    .background(
                        AppTheme.Colors.background,
                        in: UnevenRoundedRectangle(
                            topLeadingRadius: Self.bodyCornerRadius,
                            topTrailingRadius: Self.bodyCornerRadius,
                            style: .continuous
                        )
                    )
                    .background(headerColor)
            }
        }
        .ignoresSafeArea(edges: .top)
    }

    private func sideBySideContent(detail: DetailUiModel, headerColor: Color, topInset: CGFloat) -> some View {
        HStack(spacing: 0) {
            DetailArtwork(
                imageURL: detail.imageUrl,
                shinyImageURL: detail.shinyImageUrl,
                backgroundColor: headerColor,
                topPadding: 0,
                fillsHeight: true
            )
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(headerColor)

            ScrollView {
                detailBody(detail: detail)
                    .padding(.top, topInset + topBarHeight)
            }
            .frame(maxWidth: .infinity)
        }
        .ignoresSafeArea(edges: .top)
    }

    private func detailBody(detail: DetailUiModel) -> some View {
        let typeColors = (detail.types.first ?? PokemonType.unknown).colors

        return VStack(alignment: .leading, spacing: AppTheme.Spacing.md) {
            HStack {
                HStack(spacing: AppTheme.Spacing.sm) {
                    ForEach(detail.types, id: \.ordinal) { type in
                        PokemonTypeChip(type: type)
                    }
                }

                Spacer(minLength: AppTheme.Spacing.sm)

                Text(StringExtKt.formatPokedexNumber(id: detail.id))
                    .appFont(AppTheme.Typography.monoLarge)
                    .foregroundStyle(AppTheme.Colors.contentSecondary)
            }

            Text(StringExtKt.toFormattedName(detail.name))
                .appFont(AppTheme.Typography.headlineLarge)
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .frame(maxWidth: .infinity, alignment: .leading)

            HStack(spacing: AppTheme.Spacing.sm) {
                DetailAboutItemCard(
                    label: "detail.weight",
                    imageName: "ic_weight",
                    value: detail.weight,
                    typeColors: typeColors
                )
                .frame(maxWidth: .infinity)

                DetailAboutItemCard(
                    label: "detail.height",
                    imageName: "ic_ruler",
                    value: detail.height,
                    typeColors: typeColors
                )
                .frame(maxWidth: .infinity)
            }

            DetailAboutItemCard(
                label: "detail.moves",
                imageName: "ic_move",
                value: movesText(detail: detail),
                typeColors: typeColors
            )

            DetailBaseStatsCard(detail: detail)
        }
        .padding(AppTheme.Spacing.lg)
    }

    private func movesText(detail: DetailUiModel) -> String {
        detail.abilities
            .filter { !$0.isHidden }
            .map { StringExtKt.toFormattedName($0.name) }
            .joined(separator: "\n")
    }
}
