package de.stylextv.maple.render.mesh;

import org.joml.Vector3f;

public abstract class Mesh {
	
	private Vector3f[] vertices;
	
	public abstract void create();
	
	public Vector3f[] getVertices() {
		if(vertices == null) create();
		
		return vertices;
	}
	
	public void setVertices(Vector3f[] vertices) {
		this.vertices = vertices;
	}
	
}
