package com.mawar.bsecure.ui.view.community

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mawar.bsecure.R
import com.mawar.bsecure.model.post.Post
import com.mawar.bsecure.repository.CommunityRepository
import com.mawar.bsecure.ui.helper.Formatter.formatTimestamp
import com.mawar.bsecure.ui.viewModel.community.CommunityViewModel
import com.mawar.bsecure.ui.viewModel.community.CommunityViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(onPostClick: (Post) -> Unit, onCommentClick: (Post) -> Unit, navApp: NavController, uid: String) {
    val communityViewModel: CommunityViewModel = viewModel(factory = CommunityViewModelFactory(CommunityRepository()))

    // Load posts when the screen is first displayed
    LaunchedEffect(true) {
        communityViewModel.loadPosts()
    }

    // Observe state from ViewModel
    val posts by communityViewModel.posts.collectAsState(initial = emptyList())
    val isLoading by communityViewModel.isLoading.collectAsState()
    val errorMessage by communityViewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeaderSection(navApp, title = "Komunitas")

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (!errorMessage.isNullOrEmpty()) {
            Text(text = "Error: $errorMessage", color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(posts) { post ->
                    CommunityPostItem(
                        post = post,
                        userData = communityViewModel.userCache[post.uid] ?: emptyMap(),
                        onClick = { onPostClick(post) },
                        onLikeClick = { communityViewModel.toggleLike(post, uid) },
                        onCommentClick = { onCommentClick(post) },
                        likesCount = communityViewModel.likesCount.collectAsState().value[post.id] ?: 0
                    )
                    Divider(thickness = 0.5.dp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = {
            Log.d("CommunityScreen", "Navigating to addCommunity with UID: $uid")
            navApp.navigate("addCommunity/$uid")
        }) {
            Icon(
                painter = painterResource(id = R.drawable.apple),
                contentDescription = "Add New Post",
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun HeaderSection(navApp: NavController, title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5A2D82))
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navApp.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.weight(0.65f))

            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun CommunityPostItem(
    post: Post,
    userData: Map<String, Any>?,
    onClick: () -> Unit,
    onLikeClick: (Post) -> Unit,
    onCommentClick: (Post) -> Unit,
    likesCount: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        PostItemUserSection(
            profilePictureUrl = userData?.get("profilePictureUrl").toString(),
            username = userData?.get("username") as? String ?: "User"
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = post.content, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onCommentClick(post) }) {
                    Icon(painter = painterResource(id = R.drawable.outline_comment_24), contentDescription = "Comment")
                }

                IconButton(onClick = { onLikeClick(post) }) {
                    Icon(
                        painter = painterResource(id = if (likesCount > 0) R.drawable.like else R.drawable.like),
                        contentDescription = "Like"
                    )
                    Text(text = likesCount.toString(), color = Color.Gray, fontSize = 12.sp)
                }

                Text(text = formatTimestamp(post.timestamp), color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun PostItemUserSection(profilePictureUrl: String?, username: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = profilePictureUrl,
            contentDescription = username,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = username, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
