package de.stylextv.maple.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.pathing.calc.Node;
import de.stylextv.maple.render.mesh.BoxMesh;
import de.stylextv.maple.render.mesh.LineMesh;
import de.stylextv.maple.render.mesh.Mesh;
import de.stylextv.maple.scheme.Color;

public class ShapeRenderer {

	public static void drawBox(MatrixStack matrices, VertexConsumerProvider provider,
							   BlockPos pos, Color color, float lineWidth) {
		drawBox(matrices, provider, pos, pos, color, lineWidth);
	}

	public static void drawBox(MatrixStack matrices, VertexConsumerProvider provider,
							   BlockPos pos1, BlockPos pos2, Color color, float lineWidth) {
		int x1 = Math.min(pos1.getX(), pos2.getX());
		int y1 = Math.min(pos1.getY(), pos2.getY());
		int z1 = Math.min(pos1.getZ(), pos2.getZ());
		int x2 = Math.max(pos1.getX(), pos2.getX());
		int y2 = Math.max(pos1.getY(), pos2.getY());
		int z2 = Math.max(pos1.getZ(), pos2.getZ());

		Mesh mesh = BoxMesh.getMesh(x2 - x1 + 1, y2 - y1 + 1, z2 - z1 + 1);
		Vector3f origin = new Vector3f(x1, y1, z1);
		drawMesh(matrices, provider, mesh, origin, color, lineWidth);
	}

	public static void drawNodeConnection(MatrixStack matrices, VertexConsumerProvider provider,
										  Node node, Color color, float lineWidth) {
		Node parent = node.getParent();
		Mesh mesh = LineMesh.getMesh(
				node.getX() - parent.getX(),
				node.getY() - parent.getY(),
				node.getZ() - parent.getZ()
		);
		Vec3d center = Vec3d.ofCenter(parent.blockPos());
		Vector3f origin = new Vector3f((float) center.x, (float) center.y, (float) center.z);
		drawMesh(matrices, provider, mesh, origin, color, lineWidth);
	}

	public static void drawLine(MatrixStack matrices, VertexConsumerProvider provider,
								Vec3d start, Vec3d end, Color color) {
		VertexConsumer consumer = provider.getBuffer(RenderLayer.getLines());
		MatrixStack.Entry entry = matrices.peek();
		org.joml.Matrix4f mat = entry.getPositionMatrix();
		Vector4f col = color.asVector();

		consumer.vertex(mat, (float) start.x, (float) start.y, (float) start.z)
				.color(col.x(), col.y(), col.z(), col.w());
		consumer.vertex(mat, (float) end.x,   (float) end.y,   (float) end.z)
				.color(col.x(), col.y(), col.z(), col.w());
	}

	private static void drawMesh(MatrixStack matrices, VertexConsumerProvider provider,
								 Mesh mesh, Vector3f pos, Color color, float lineWidth) {
		VertexConsumer consumer = provider.getBuffer(RenderLayer.getLines());
		MatrixStack.Entry entry = matrices.peek();
		org.joml.Matrix4f mat = entry.getPositionMatrix();

		Vec3d cam = GameContext.cameraPosition();
		Vector4f col = color.asVector();
		float r = col.x(), g = col.y(), b = col.z(), a = col.w();

		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(lineWidth);

		for (Vector3f v : mesh.getVertices()) {
			float x = v.x() + pos.x() - (float) cam.x;
			float y = v.y() + pos.y() - (float) cam.y;
			float z = v.z() + pos.z() - (float) cam.z;

			consumer.vertex(mat, x, y, z).color(r, g, b, a).normal(entry, 0.0F, 1.0F, 0.0F);
		}

		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}
}