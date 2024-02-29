// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.Shooter;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class stopShooterConveyor extends ParallelCommandGroup {
  /** Creates a new stopShooterConveyor. */
  public stopShooterConveyor(Conveyor m_Conveyor, shooter m_Shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> m_Conveyor.stopConveyor()),
      //new setArmPosition(m_Arm, 0.05),
      new InstantCommand(() -> m_Shooter.stop())
    );
  }
}
