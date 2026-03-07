package com.axiel7.moelist.main.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.axiel7.moelist.data.model.media.MediaType
import com.axiel7.moelist.ui.base.navigation.NavActionManager
import com.axiel7.moelist.ui.composables.PlatformImage
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.ic_round_account_circle_24
import com.axiel7.moelist.ui.generated.resources.ic_round_search_24
import com.axiel7.moelist.ui.generated.resources.search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainTopAppBar(
    profilePicture: String?,
    isVisible: Boolean,
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = isVisible,
        transitionSpec = {
            slideInVertically(initialOffsetY = { -it }) togetherWith
                    slideOutVertically(targetOffsetY = { -it })
        }
    ) { isVisible ->
        if (isVisible) {
            Card(
                onClick = dropUnlessResumed { navActionManager.toSearch(MediaType.ANIME) },
                modifier = modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
                shape = RoundedCornerShape(50),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(UiRes.drawable.ic_round_search_24),
                        contentDescription = "search",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(UiRes.string.search),
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    PlatformImage(
                        model = profilePicture,
                        contentDescription = "profile",
                        placeholder = painterResource(UiRes.drawable.ic_round_account_circle_24),
                        error = painterResource(UiRes.drawable.ic_round_account_circle_24),
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .size(32.dp)
                            .clickable { navActionManager.toProfile() }
                    )
                }
            }//:Card
        } else {
            Box(modifier = Modifier.fillMaxWidth())
        }
    }
}
