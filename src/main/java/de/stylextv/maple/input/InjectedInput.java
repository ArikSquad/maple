package de.stylextv.maple.input;

import de.stylextv.maple.input.controller.InputController;
import net.minecraft.client.input.Input;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec2f;

public class InjectedInput extends Input {

	@Override
	public void tick() {
		boolean forward = InputController.isPressed(InputAction.MOVE_FORWARD);
		boolean back = InputController.isPressed(InputAction.MOVE_BACK);
		boolean left = InputController.isPressed(InputAction.MOVE_LEFT);
		boolean right = InputController.isPressed(InputAction.MOVE_RIGHT);
		boolean jump = InputController.isPressed(InputAction.JUMP);
		boolean sneak = InputController.isPressed(InputAction.SNEAK);
		boolean sprint = InputController.isPressed(InputAction.SPRINT);

		// determine directional movement (-1 to 1)
		float forwardVal = (forward == back) ? 0 : (forward ? 1.0f : -1.0f);
		float sidewaysVal = (right == left) ? 0 : (right ? 1.0f : -1.0f);

		// set the movement vector
		this.movementVector = new Vec2f(sidewaysVal, forwardVal);
		this.playerInput = new PlayerInput(
				forward, back, left, right, jump, sneak, sprint
		);

		InputController.releaseAll();
	}
}
