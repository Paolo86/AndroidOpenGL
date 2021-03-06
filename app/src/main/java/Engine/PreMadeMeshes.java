package Engine;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import Math.Vertex;
import com.blogspot.androidcanteen.androidopengl.GlobalVariables;
import com.blogspot.androidcanteen.androidopengl.R;

import java.util.ArrayList;
import java.util.HashMap;

import Rendering.Mesh;

public class PreMadeMeshes {

    public static HashMap<String, Mesh> allMeshes;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void Initialize()
    {
        allMeshes = new HashMap<>();

        allMeshes.put("Quad",createQuad());
        allMeshes.put("Sphere",Utils.loadOBJ("sphere_texture.obj"));
       allMeshes.put("Cube",createCube());
       allMeshes.put("Charizard",Utils.loadOBJ("charizard.obj"));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static Mesh getMeshByName(String name)
    {

        Mesh m = null;

        if(allMeshes.containsKey(name))
            m = allMeshes.get(name);
        else
            Log.d(GlobalVariables.TAG, "Mesh " + name + " does not exist");

        return new Mesh(m.getVertices(), m.getIndices(),false);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static Mesh createQuad()
    {
        Vertex[] vertices = {new Vertex(-1.0f,-1.0f,0.0f,0.0f,0.0f),
                new Vertex(1.0f,-1.0f,0.0f,1.0f,0.0f),
                new Vertex(1.0f,1.0f,0.0f,1.0f,1.0f),
                new Vertex(-1.0f,1.0f,0.0f,0.0f,1.0f)};

        int[] indices = {0,1,2,0,2,3};

        return new Mesh(vertices,indices,false);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static Mesh getGridMesh(int sx, int sz)
    {
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();


        for (float z = 0; z < sz; z++)
        {
            for (float x = 0; x < sx; x++)
            {

                Vertex v = new Vertex();
                v.getPosition().x = x;
                v.getPosition().y = 0;
                v.getPosition().z = -z;

                //Temp normals
                v.getNormal().x = 0.0f;
                v.getNormal().y = 0.0f;
                v.getNormal().z = 0.0f;

                v.getTextureCoords().x = x / sx;
                v.getTextureCoords().y = z / sz;


                vertices.add(v);

            }
        }

        int indexIndex = 0;
        int counter = 0;
        for (int i = 0; i < vertices.size(); i++)
        {

            if (counter >= sx  - 1)
            {
                counter = 0;
                continue;
            }

            if (i >= vertices.size() - sx - 1)
                break;

            //Face 1

            indices.add(i);
            indices.add(i + 1);
            indices.add(i + sx + 1);

            //Face 2
            indices.add(i + sx + 1);
            indices.add(i + sx);
            indices.add(i);


            counter++;

        }

        int[] inds = new int[indices.size()];

        for(int i=0; i< inds.length; i++)
            inds[i] = indices.get(i);

        Vertex[] verts = new Vertex[vertices.size()];
        vertices.toArray(verts);


        return new Mesh(verts,inds,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static Mesh createCube()
    {
        //Vertices are duplicated for the cube, basically making indices rendering useless. Necessary for correct normals
        ArrayList<Vertex> vertices = new ArrayList<>();


        //Front
       vertices.add(new Vertex(-1,-1,1,0,0)); //A
        vertices.add(new Vertex(1,-1,1,1,0)); //B
        vertices.add(new Vertex(1,1,1,1,1)); //C

        vertices.add(new Vertex(1,1,1,1,1)); //C
        vertices.add(new Vertex(-1,1,1,0,1)); //D
        vertices.add(new Vertex(-1,-1,1,0,0)); //A

        //Right
        vertices.add(new Vertex(1,-1,1,1,0)); //B
        vertices.add(new Vertex(1,-1,-1,0,1)); //E
        vertices.add(new Vertex(1,1,-1,1,1)); //F

        vertices.add(new Vertex(1,1,-1,1,1)); //F
        vertices.add(new Vertex(1,1,1,1,1)); //C
        vertices.add(new Vertex(1,-1,1,1,0)); //B

        //Back
        vertices.add(new Vertex(1,-1,-1,0,1)); //E
        vertices.add(new Vertex(-1,-1,-1,0,0)); //H
        vertices.add(new Vertex(-1,1,-1,1,1)); //G

        vertices.add(new Vertex(-1,1,-1,1,1)); //G
        vertices.add(new Vertex(1,1,-1,1,1)); //F
        vertices.add(new Vertex(1,-1,-1,0,1)); //E

        //Left
        vertices.add(new Vertex(-1,-1,-1,0,0)); //H
        vertices.add(new Vertex(-1,-1,1,0,0)); //A
        vertices.add(new Vertex(-1,1,1,0,1)); //D

        vertices.add(new Vertex(-1,1,1,0,1)); //D
        vertices.add(new Vertex(-1,1,-1,1,1)); //G
        vertices.add(new Vertex(-1,-1,-1,0,0)); //H

        //Top
        vertices.add(new Vertex(-1,1,1,0,1)); //D
        vertices.add(new Vertex(1,1,1,1,1)); //C
        vertices.add(new Vertex(1,1,-1,1,1)); //F

        vertices.add(new Vertex(1,1,-1,1,1)); //F
        vertices.add(new Vertex(-1,1,-1,1,1)); //G
        vertices.add(new Vertex(-1,1,1,0,1)); //D

        //Bottom
        vertices.add(new Vertex(-1,-1,-1,0,0)); //H
        vertices.add(new Vertex(1,-1,-1,0,1)); //E
        vertices.add(new Vertex(1,-1,1,1,0)); //B

        vertices.add(new Vertex(1,-1,1,1,0)); //B
        vertices.add(new Vertex(-1,-1,1,0,0)); //A
        vertices.add(new Vertex(-1,-1,-1,0,0)); //H

        int[] indices = new int[vertices.size()];
        for(int i=0; i< vertices.size(); i++)
            indices[i] = i;

        Vertex[] verts = new Vertex[vertices.size()];
        vertices.toArray(verts);
        return new Mesh(verts,indices,false);

    }


}
