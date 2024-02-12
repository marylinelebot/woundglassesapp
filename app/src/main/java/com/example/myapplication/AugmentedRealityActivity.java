package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;

public class AugmentedRealityActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private ImageButton muteButton;
    private boolean isMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_reality);

        // Get the URI of the video file
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);

        // Initialize the VideoView and set the video URI
        mVideoView = findViewById(R.id.videoView);
        mVideoView.setVideoURI(videoUri);

        // Create and set custom media controller
        CustomMediaController mediaController = new CustomMediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);

        // Start playing the video
        mVideoView.start();
    }

    //Method to end a call
    public void stopVideoAndReturnToMainActivity() {
        // Stop the video
        mVideoView.stopPlayback();
        // Return to the main activity
        startActivity(new Intent(this, MainActivity.class));
    }

    // Method to mute the microphone
    //private VideoView mVideoView;
    //    private AudioManager mAudioManager;
    //
    //    @Override
    //    protected void onCreate(Bundle savedInstanceState) {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_augmented_reality);
    //
    //        // Get the URI of the video file
    //        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
    //
    //        // Initialize the VideoView and set the video URI
    //        mVideoView = findViewById(R.id.videoView);
    //        mVideoView.setVideoURI(videoUri);
    //
    //        // Create and set custom media controller
    //        CustomMediaController mediaController = new CustomMediaController(this);
    //        mediaController.setAnchorView(mVideoView);
    //        mVideoView.setMediaController(mediaController);
    //
    //        // Start playing the video
    //        mVideoView.start();
    //
    //        // Initialize AudioManager
    //        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    //    }
    //
    //    public void muteMicrophone() {
    //        // Mute the microphone
    //        mAudioManager.setMicrophoneMute(true);
    //    }
    //
    //    public void unmuteMicrophone() {
    //        // Unmute the microphone
    //        mAudioManager.setMicrophoneMute(false);
    //    }
    // Method to toggle mute/unmute
    public void toggleMute(ImageButton button) {
        if (mVideoView != null) {
            isMuted = !isMuted;
            try {
                Field field = VideoView.class.getDeclaredField("mMediaPlayer");
                field.setAccessible(true);
                MediaPlayer mediaPlayer = (MediaPlayer) field.get(mVideoView);
                if (mediaPlayer != null) {
                    if (isMuted) {
                        mediaPlayer.setVolume(0, 0);
                        button.setImageResource(R.drawable.mute_mic);

                    } else {
                        mediaPlayer.setVolume(1, 1);
                        button.setImageResource(R.drawable.unmute_mic);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}