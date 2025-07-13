package de.stylextv.maple.event.listeners;

import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.event.EventListener;
import de.stylextv.maple.event.events.RenderWorldEvent;
import de.stylextv.maple.pathing.PathingExecutor;
import de.stylextv.maple.pathing.calc.Path;
import de.stylextv.maple.pathing.calc.PathSegment;
import de.stylextv.maple.pathing.calc.SearchExecutor;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.pathing.movement.Movement;
import de.stylextv.maple.pathing.movement.MovementExecutor;
import de.stylextv.maple.render.NameTagRenderer;
import de.stylextv.maple.render.ShapeRenderer;
import de.stylextv.maple.scheme.Color;
import de.stylextv.maple.scheme.Colors;
import de.stylextv.maple.waypoint.Waypoint;
import de.stylextv.maple.waypoint.Waypoints;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class RenderListener implements EventListener {
	
	private static final int BEAM_RENDER_DISTANCE = 250 * 250;
	
	private static final int BEAM_HEIGHT = 1024;
	
	@Override
	public void onEntitiesRender(RenderWorldEvent event) {
		if(!WorldContext.isInWorld()) return;
		
		PathSegment s = SearchExecutor.getLastConsideration();
		
		PathSegment best = SearchExecutor.getBestSoFar();
		
		drawPathSegment(event, s, Colors.PATH_CALCULATION);
		drawPathSegment(event, best, Colors.BEST_PATH_SO_FAR);
		
		Goal goal = PathingExecutor.getGoal();
		
		if(goal != null) goal.render(event);
		
		Path path = MovementExecutor.getPath();
		
		drawPath(event, path, Colors.PATH);
		
		drawWaypoints(event);
	}
	
	private void drawPath(RenderWorldEvent event, Path path, Color color) {
		if(path == null) return;
		
		for(PathSegment s : path.getAllSegments()) {
			drawPathSegment(event, s, color);
		}
	}
	
	private void drawPathSegment(RenderWorldEvent event, PathSegment s, Color color) {
		if(s == null) return;
		
		for(int i = s.getPointer(); i < s.length(); i++) {
			
			Movement m = s.getMovement(i);
			
			m.render(event, color);
		}
	}
	
	private void drawWaypoints(RenderWorldEvent event) {
		for(Waypoint p : Waypoints.getWaypoints()) {
			drawWaypoint(event, p);
		}
	}
	
	private void drawWaypoint(RenderWorldEvent event, Waypoint p) {
		if(!p.isInWorld()) return;
		
		double dis = p.squaredDistance();
		
		if(dis > BEAM_RENDER_DISTANCE) return;
		
		BlockPos pos = p.getPos();
		
		float x = pos.getX() + 0.5f;
		float y = pos.getY() + 1.4f;
		float z = pos.getZ() + 0.5f;
		
		Vector3f v1 = new Vector3f(x, pos.getY(), z);
		Vector3f v2 = new Vector3f(x, BEAM_HEIGHT, z);

		Vec3d start = new Vec3d(v1.x(), v1.y(), v1.z());
		Vec3d end = new Vec3d(v2.x(), v2.y(), v2.z());

		ShapeRenderer.drawLine(
				event.getMatrixStack(),
				event.getVertexConsumerProvider(),
				start,
				end,
				Colors.WAYPOINT
		);
		
		String s = "§f" + p.getName();
		
		NameTagRenderer.drawTag(event, s, x, y, z);
	}
	
}
