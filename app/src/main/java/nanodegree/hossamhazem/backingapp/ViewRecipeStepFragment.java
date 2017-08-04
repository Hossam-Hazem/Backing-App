package nanodegree.hossamhazem.backingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.hossamhazem.backingapp.utils.MediaType;
import nanodegree.hossamhazem.backingapp.utils.RecipeStep;


public class ViewRecipeStepFragment extends Fragment {
    @BindView(R.id.videoView) SimpleExoPlayerView simpleExoPlayerView;
    @Nullable @BindView(R.id.recipeStepDescription) TextView recipeStepDescription;
    @Nullable @BindView(R.id.prevRecipeStepButton) Button prevRecipeStepButton;
    @Nullable @BindView(R.id.nextRecipeStepButton) Button nextRecipeStepButton;
    private SimpleExoPlayer mExoPlayer;

    RecipeStep recipeStep;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey("recipeStep")){
            recipeStep = savedInstanceState.getParcelable("recipeStep");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipeStep", recipeStep);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_view_recipe_step, container, false);
        ButterKnife.bind(this, view);
        bind();

        return view;
    }

    public void setRecipeStep(RecipeStep recipeStep){
        this.recipeStep = recipeStep;
    }

    public void bind(){
        if(recipeStepDescription != null) {
            String desc = recipeStep.getDescription();
            recipeStepDescription.setText(desc);
        }

        if(recipeStep.getMediaType() == MediaType.VIDEO)
            initExoPlayer(recipeStep.getMediaURL());
        else
            simpleExoPlayerView.setVisibility(View.GONE);


        if(prevRecipeStepButton != null && nextRecipeStepButton != null) {
            prevRecipeStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RecipeStepFragmentActivityInterface) getActivity()).prevRecipeStepOnClick(recipeStep);
                }
            });

            nextRecipeStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RecipeStepFragmentActivityInterface) getActivity()).nextRecipeStepOnClick(recipeStep);
                }
            });
        }
    }

    private void initExoPlayer(Uri mediaUri){
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(recipeStep.getMediaType() == MediaType.VIDEO)
            releasePlayer();
    }



    interface RecipeStepFragmentActivityInterface{
        void nextRecipeStepOnClick(RecipeStep recipeStep);
        void prevRecipeStepOnClick(RecipeStep recipeStep);
    }
}
