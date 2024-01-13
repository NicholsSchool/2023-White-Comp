package org.firstinspires.ftc.teamcode.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import org.json.JSONArray;

/**
 *  Class for Vector Waypointing using SVG Paths
 */
public class VectorPath {

    private ArrayList<double[]> points;

    /**
     * Initializes a new VectorPath from a keyframe file.
     * The Keyframe file must consist of an array of points (Float[][]/Double[][])
     * 
     * @param KeyframeAssetName The filename of the Keyframe JSON File (ex. Keyframes.json)
     * @param context Something important (try "this")
     */
    public VectorPath(String KeyframeAssetName, Context context) {

        AssetManager am = context.getAssets();

        InputStream is;

        String input;

        try {

            is = am.open(KeyframeAssetName);

        } catch (IOException e) {

            throw new IllegalStateException("Cannot find keyframe file " + KeyframeAssetName);

        }

        StringBuilder textBuilder = new StringBuilder();

        try (Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            int c = 0;

            while ((c = reader.read()) != -1) {

                textBuilder.append((char) c);

            }

            input = textBuilder.toString();

            

        } catch (IOException e) {

            throw new IllegalStateException("The inputted file could not be read as a UTF-8 text file.");

        }

        JSONArray pointsJSON = new JSONArray(input);

        if (pointsJSON != null) { 

            points = new ArrayList<double[]>();
            

            for (int i=0; i < pointsJSON.length(); i++){

                double[] point = new double[2];

                for (int j = 0; j < 2; j++) {
                    point[j] = Double.parseDouble(pointsJSON.getJSONArray(i).getString(j));
                }

                points.add(point);

            } 

        } 


    }

    /**
     * Returns a list of waypoints parsed from the input JSON waypoints file.
     * 
     * @return The list of waypoints from the JSON expressed as an ArrayList<double[]>.
     */
    public ArrayList<double[]> getWaypoints() {

        return points;

    }

    /**
     * Returns a specific waypoint from the input JSON waypoints file using an index value.
     * 
     * @param i The index of the waypoint
     * @return  The waypoint expressed as a double[].
     */
    public double[] getWaypoint(int i) {

        return points.get(i);

    }

    public void follow(Boolean useAprilTagLocalization) throws Exception {

        throw new IllegalStateException("Not Implemented");

    }
}
