package com.mawar.bsecure.ui.view.community

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.firestore.v1.StructuredAggregationQuery.Aggregation.Count
import com.mawar.bsecure.R
import com.mawar.bsecure.model.post.Post
import com.mawar.bsecure.repository.CommunityRepository
import com.mawar.bsecure.ui.helper.Formatter.formatTimestamp
import com.mawar.bsecure.ui.viewModel.community.CommunityViewModel
import com.mawar.bsecure.ui.viewModel.community.CommunityViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityPostDetailScreen(
    uid: String,
    post: Post,
    onPostClick: (Post) -> Unit,
    userData: Map<String, Any>?,
    onCommentClick: (Post) -> Unit,
    navController: NavController
) {
    val communityViewModel: CommunityViewModel =
        viewModel(factory = CommunityViewModelFactory(CommunityRepository()))
    var replyText by remember { mutableStateOf("") }

    // Load comments for the current post when the screen is displayed
    LaunchedEffect(post.id) {
        communityViewModel.loadComments(post.id)
    }

    // Observe comments from ViewModel
    val comments by communityViewModel.comments.collectAsState(initial = emptyList())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(bottom = 64.dp) // Memberikan ruang untuk TextField
        ) {
            HeadSec(uid,navController)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {

                item {
                    CommunityPostItem(
                        post = post,
                        userData = communityViewModel.userCache[post.uid] ?: emptyMap(),
                        onClick = { onPostClick(post) },
                        onLikeClick = { communityViewModel.toggleLike(post, uid) },
                        onCommentClick = { onCommentClick(post) },
                        likesCount = communityViewModel.likesCount.collectAsState().value[post.id] ?: 0,
                        commentsCount = communityViewModel.commentsCount.collectAsState().value[post.id] ?: 0
                    )

                    Divider(thickness = 0.5.dp, color = Color.Gray)
                }

                items(comments) { comment ->
                    CommentItem(
                        post = comment,
                        userData = communityViewModel.userCache[comment.uid] ?: emptyMap()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Input field tetap berada di bawah
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Menggunakan .align karena berada di dalam Box
                .padding(3.dp)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = replyText,
                onValueChange = { replyText = it },
                placeholder = { Text("Posting Balasan Anda...") },
                trailingIcon = {
                    IconButton(onClick = {
                        communityViewModel.addComment(uid = uid, content = replyText, postId = post.id)
                        replyText = "" // Hapus input setelah dikirim
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.send),
                            contentDescription = "Send",
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.Transparent)
                        )
                    }

                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFF7346A5).copy(alpha = 0.7f),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier

                    .fillMaxWidth()

                    .background(Color.Transparent)

            )


        }
    }
}
@Composable
fun HeadSec(uid: String,navController: NavController) {
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
            IconButton(onClick = { navController.navigate("community/$uid") }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.weight(0.65f))

            Text(
                text = "Komunitas",
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
fun PostDetailSection(
    post: Post,
    userData: Map<String, Any>?,
    onLikeClick: (Post) -> Unit,
    onCommentClick: (Post) -> Unit,
    likesCount: Int,
    commentsCount: Int
    ) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = userData?.get("profilePictureUrl"), // URL of the profile picture
                contentDescription = userData?.get("username") as? String ?: "User",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray, CircleShape),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = userData?.get("username") as? String ?: "Unknown",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = post.content, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { onCommentClick(post) }) {
                        Icon(painter = painterResource(id = R.drawable.outline_comment_24), contentDescription = "Comment")

                    }
                    Text(text = commentsCount.toString(),
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.offset(-30.dp)

                    )

                    IconButton(onClick = { onLikeClick(post) }) {
                        Icon(
                            painter = painterResource(id = if (post.isLikedByCurrentUser) R.drawable.apple else R.drawable.like),
                            contentDescription = "Like"
                        )
                    }

                    Text(text = likesCount.toString(),
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.offset(-30.dp)

                    )

                    Text(text = formatTimestamp(post.timestamp), color = Color.Gray, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun CommentItem(post: Post, userData: Map<String, Any>?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = userData?.get("profilePictureUrl"),
            contentDescription = userData?.get("username") as? String ?: "User",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray, CircleShape),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = userData?.get("username") as? String ?: "User",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "@${userData?.get("username") as? String ?: "User"}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = formatTimestamp(post.timestamp),
                    fontSize = 12.sp,
                    color = Color(0xFF9E9E9E)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = post.content, fontSize = 14.sp, color = Color.Black)
        }
    }
}