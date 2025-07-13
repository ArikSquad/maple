package de.stylextv.maple.render.mesh;

import java.util.HashMap;

import de.stylextv.maple.util.world.CoordUtil;
import org.joml.Vector3f;

public class LineMesh extends Mesh {
	
	private static final HashMap<Long, LineMesh> MESH_CACHE = new HashMap<>();
	
	private final float dx;
	private final float dy;
	private final float dz;
	
	public LineMesh(float dx, float dy, float dz) {
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	@Override
	public void create() {
		Vector3f[] vertices = new Vector3f[2];
		
		vertices[0] = new Vector3f(0.0f, 0.0f, 0.0f);
		vertices[1] = new Vector3f(dx, dy, dz);
		
		setVertices(vertices);
	}
	
	public float getDeltaX() {
		return dx;
	}
	
	public float getDeltaY() {
		return dy;
	}
	
	public float getDeltaZ() {
		return dz;
	}
	
	public static LineMesh getMesh(int dx, int dy, int dz) {
		long key = CoordUtil.posAsLong(dx, dy, dz);
		
		LineMesh mesh = MESH_CACHE.get(key);
		
		if(mesh == null) {
			
			mesh = new LineMesh(dx, dy, dz);
			
			MESH_CACHE.put(key, mesh);
		}
		
		return mesh;
	}
	
}
