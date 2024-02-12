package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;


// Class to customize the tool bar
public class CustomMediaController extends MediaController {

    private Context mContext;
    private AugmentedRealityActivity mActivity;
    private VideoView mVideoView;
    private boolean isMuted = false;

    public CustomMediaController(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void setAnchorView(final View view) {
        super.setAnchorView(view);
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        removeAllViews();
        View customMediaControllerView = LayoutInflater.from(getContext()).inflate(R.layout.custom_media_controller, null);
        addView(customMediaControllerView, frameParams);


        // End a call
        ImageButton endCallButton = customMediaControllerView.findViewById(R.id.end_call);
        endCallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop the video
                if (mContext instanceof AugmentedRealityActivity) {
                    ((AugmentedRealityActivity) mContext).stopVideoAndReturnToMainActivity();
                }
            }
        });

        // Mute mic
        ImageButton muteButton = customMediaControllerView.findViewById(R.id.mute_mic);
        muteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop the video
                if (mContext instanceof AugmentedRealityActivity) {
                    ((AugmentedRealityActivity) mContext).toggleMute(muteButton);
                }
            }
        });

        // Add other custom media controller buttons as needed
    }

}
