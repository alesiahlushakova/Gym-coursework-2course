package by.training.gym.controller.command;

/**
 * class for command types.
 * @author AlesyaHlushakova
 */
public enum CommandType {

    /**
     * commands.
     */
    COMMON_LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    COMMON_LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    COMMON_REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    COMMON_CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    };
/**
 * todo add commands.
 */


    /**
     * Current command.
     */
    CommandAction command;

    /**
     * Gets current commands.
     *
     * @return the current commands.
     */
    public CommandAction getCurrentCommand() {
        return command;
    }
}
