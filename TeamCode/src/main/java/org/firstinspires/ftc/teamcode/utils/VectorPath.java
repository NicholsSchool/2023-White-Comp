package org.firstinspires.ftc.teamcode.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import android.content.res.AssetManager;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.robot.DriveTrain;

import org.json.JSONArray;
import org.json.JSONException;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 *  Class for Vector Waypointing using SVG Paths
 */
public class VectorPath {

    private ArrayList<double[]> points;

    private DriveTrain dt;

    /**
     * Initializes a new VectorPath from a keyframe file.
     * The Keyframe file must consist of an array of points (Float[][]/Double[][])
     * 
     * @param KeyframeAssetName The filename of the Keyframe JSON File (ex. Keyframes.json)
     */
    public VectorPath(HardwareMap hwMap, String KeyframeAssetName) {

        AssetManager am = AppUtil.getDefContext().getAssets();

        InputStream is;

        String input;

        dt = new DriveTrain(hwMap, 0, 0, Math.PI/2);

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

        JSONArray pointsJSON;
        try {
            pointsJSON = new JSONArray(input);
        } catch (JSONException e) {
            throw new IllegalStateException("The inputted file could not be parsed as a JSON Array.");
        }

        if (pointsJSON != null) { 

            points = new ArrayList<double[]>();
            

            for (int i=0; i < pointsJSON.length(); i++){

                double[] point = new double[2];

                for (int j = 0; j < 2; j++) {
                    try {
                        point[j] = Double.parseDouble(pointsJSON.getJSONArray(i).getString(j));
                    } catch (JSONException e) {
                        throw new IllegalStateException("Array Index out of range (if this happens, something is very wrong)");
                    }
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

    public void follow(Boolean useAprilTagLocalization) {

        for (double[] point : points) {
            
            while (!dt.driveToPosition(point[0], point[1], 0.8, 0.1)) {

                //nothing lol

            };

        }

    }
}
