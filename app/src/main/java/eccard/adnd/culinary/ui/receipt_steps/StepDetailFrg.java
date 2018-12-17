package eccard.adnd.culinary.ui.receipt_steps;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Step;

public class StepDetailFrg extends Fragment {

    public static final String TAG = StepDetailFrg.class.getSimpleName();
    private static final String EXTRA_PLAYER_TIME_POSITION = "player-position-time";

    private PlayerView playerView;
    private long playerTimePosition;
    private ExoPlayer exoPlayer;
    private TextView tvStepDescription;

    private Step stepExtra;
    public static StepDetailFrg newInstance(Step step) {

        Bundle args = new Bundle();
        args.putParcelable(Step.class.getSimpleName(),step);

        StepDetailFrg fragment = new StepDetailFrg();
        fragment.setArguments(args);
        return fragment;
    }


    private void setExtras(){
        if ( getArguments() != null ){
            stepExtra = getArguments().getParcelable(Step.class.getSimpleName());

        }
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setExtras();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_step_detail,container,false);

        playerView = view.findViewById(R.id.playerView);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        tvStepDescription = view.findViewById(R.id.tv_step_description);

        if (stepExtra.getDescription() != null){
            tvStepDescription.setText(stepExtra.getDescription());
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null){
            playerTimePosition = savedInstanceState.getLong(EXTRA_PLAYER_TIME_POSITION, 0L);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23 && stepExtra != null) {
            setUpPlayer(stepExtra.getVideoURL());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || exoPlayer == null) && stepExtra != null) {
            setUpPlayer(stepExtra.getVideoURL());
        }
    }

    private void setUpPlayer(String stepUrlPath) {

        if(stepUrlPath != null && !stepUrlPath.isEmpty()) {

            TrackSelector trackSelector = new DefaultTrackSelector();

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            exoPlayer.prepare(buildMediaSource(Uri.parse(stepUrlPath)), false, true);
            exoPlayer.seekTo(playerTimePosition);
            exoPlayer.setPlayWhenReady(true);

            playerView.setPlayer(exoPlayer);

        } else {
            playerView.setVisibility(View.GONE);
        }
    }


    private ExtractorMediaSource buildMediaSource(Uri uri) {

        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(getActivity(), "CulinaryApp"))
                .createMediaSource(uri);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    private void releasePlayer() {
        if (exoPlayer != null) {
            playerTimePosition = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(exoPlayer != null) {
            outState.putLong(EXTRA_PLAYER_TIME_POSITION, exoPlayer.getCurrentPosition());
        }
    }

}
