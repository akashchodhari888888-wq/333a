package com.example.data

import androidx.annotation.DrawableRes
import com.example.R

data class TutorialStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val imageRes: Int? = null,
    val tip: String? = null
)

data class Tutorial(
    val id: String,
    val title: String,
    val intro: String,
    val videoUrl: String? = null,
    val steps: List<TutorialStep>
)

object TutorialData {
    val tutorials = listOf(
        Tutorial(
            id = "home",
            title = "How to Download from Instagram",
            intro = "Follow these simple steps to download any video, photo, reel, or IGTV from Instagram.",
            steps = listOf(
                TutorialStep(1, "Open Instagram", "Open the Instagram app on your device."),
                TutorialStep(2, "Find Content", "Find the video or photo you want to download."),
                TutorialStep(3, "Copy Link", "Tap the three dots (⋮) and select 'Copy Link'."),
                TutorialStep(4, "Paste in SaveGram", "Open SaveGram and tap the 'Paste' button."),
                TutorialStep(5, "Download", "Tap 'Download' and choose your preferred quality.")
            )
        ),
        Tutorial(
            id = "story",
            title = "How to Save Stories",
            intro = "Save stories anonymously from any public Instagram account.",
            steps = listOf(
                TutorialStep(1, "Find Username", "Get the username of the public account."),
                TutorialStep(2, "Enter Username", "Enter the username in the Story Saver tab."),
                TutorialStep(3, "Browse Stories", "Wait for the stories to load."),
                TutorialStep(4, "Download", "Tap the download icon on the story you want to save.")
            )
        ),
        Tutorial(
            id = "viewer",
            title = "Anonymous Viewer Guide",
            intro = "View profiles and stories anonymously without logging in.",
            steps = listOf(
                TutorialStep(1, "Search Account", "Enter the username of the profile you want to view."),
                TutorialStep(2, "View Profile", "See bio, profile picture, and follower count."),
                TutorialStep(3, "Watch Stories", "Tap on available stories to view them anonymously.")
            )
        )
        // Add more tutorials as needed...
    )
}
