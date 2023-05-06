package com.example.forestoflife;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ForestFragment extends Fragment {

    public ForestFragment() {
        // Required empty public constructor
    }

    private SceneView backgroundSceneView;
    private SceneView transparentSceneView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forest, container, false);

        backgroundSceneView = view.findViewById(R.id.backgroundSceneView);
        transparentSceneView = view.findViewById(R.id.transparentSceneView);
        transparentSceneView.setTransparent(true);

        System.out.println("Forest");

        loadModels();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            backgroundSceneView.resume();
            transparentSceneView.resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        backgroundSceneView.pause();
        transparentSceneView.pause();
    }

    public void loadModels() {

        CompletableFuture<ModelRenderable> dragon = ModelRenderable
                .builder()
                .setSource(getActivity()
                        , Uri.parse("models/dragon.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();

        CompletableFuture<ModelRenderable> backdrop = ModelRenderable
                .builder()
                .setSource(getActivity()
                        , Uri.parse("models/dargon_backdrop.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();


        CompletableFuture.allOf(dragon, backdrop)
                .handle((ok, ex) -> {
                    try {
                        Node modelNode1 = new Node();
                        modelNode1.setRenderable(dragon.get());
                        modelNode1.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
                        modelNode1.setLocalRotation(Quaternion.multiply(
                                Quaternion.axisAngle(new Vector3(1f, 0f, 0f), 45),
                                Quaternion.axisAngle(new Vector3(0f, 1f, 0f), 75)));
                        modelNode1.setLocalPosition(new Vector3(0f, 0f, -1.0f));
                        backgroundSceneView.getScene().addChild(modelNode1);

                        Node modelNode2 = new Node();
                        modelNode2.setRenderable(backdrop.get());
                        modelNode2.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
                        modelNode2.setLocalRotation(Quaternion.multiply(
                                Quaternion.axisAngle(new Vector3(1f, 0f, 0f), 45),
                                Quaternion.axisAngle(new Vector3(0f, 1f, 0f), 75)));
                        modelNode2.setLocalPosition(new Vector3(0f, 0f, -1.0f));
                        backgroundSceneView.getScene().addChild(modelNode2);

                        Node modelNode3 = new Node();
                        modelNode3.setRenderable(dragon.get());
                        modelNode3.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
                        modelNode3.setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 1f, 0f), 35));
                        modelNode3.setLocalPosition(new Vector3(0f, 0f, -1.0f));
                        transparentSceneView.getScene().addChild(modelNode3);

                        Node modelNode4 = new Node();
                        modelNode4.setRenderable(backdrop.get());
                        modelNode4.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
                        modelNode4.setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 1f, 0f), 35));
                        modelNode4.setLocalPosition(new Vector3(0f, 0f, -1.0f));
                        transparentSceneView.getScene().addChild(modelNode4);
                    } catch (InterruptedException | ExecutionException ignore) {

                    }
                    return null;
                });
    }
}