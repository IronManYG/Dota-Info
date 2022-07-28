package com.example.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.example.components.DefaultScreenUI
import com.example.core.domain.UIComponentState
import com.example.ui_herolist.components.HeroListFilter
import com.example.ui_herolist.components.HeroListItem
import com.example.ui_herolist.components.HeroListToolbar

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun HeroList(
    state: HeroListState,
    events: (HeroListEvents) -> Unit,
    imageLoader: ImageLoader,
    navigateToDetailScreen: (Int) -> Unit,
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = {
            // TODO(remove head message)
        },
        progressBarState = state.progressBarState,
    ) {
        Column {
            HeroListToolbar(
                heroName = state.heroName,
                onHeroNameChanged = { heroName ->
                    events(HeroListEvents.UpdateHeroName(heroName))
                },
                onExecuteSearch = {
                    events(HeroListEvents.FilterHeros)
                },
                onShowFilterDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Show))
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.filteredHeros) { hero ->
                    HeroListItem(
                        hero = hero,
                        onSelectHero = { heroId ->
                            navigateToDetailScreen(heroId)
                        },
                        imageLoader = imageLoader,
                    )
                }
            }
        }
        if (state.filterDialogState is UIComponentState.Show) {
            HeroListFilter(
                heroFilter = state.heroFilter,
                onUpdateHeroFilter = { heroFilter ->
                    events(HeroListEvents.UpdateHeroFilter(heroFilter))
                },
                attributeFilter = state.primaryAttrFilter,
                onUpdateAttributeFilter = { attribute ->
                    events(HeroListEvents.UpdateAttributeFilter(attribute))
                },
                onCloseDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UIComponentState.Hide))
                }
            )

        }
    }

}

