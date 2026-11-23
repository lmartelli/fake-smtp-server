package de.gessnerfl.fakesmtp.config;

import de.gessnerfl.fakesmtp.smtp.command.*;
import de.gessnerfl.fakesmtp.smtp.server.RequireAuthCommandWrapper;
import de.gessnerfl.fakesmtp.smtp.server.RequireTLSCommandWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmtpCommandConfig {

    @Bean
    public CommandHandler commandHandler(){
        return new CommandHandler(commandRegistry());
    }

    public CommandRegistry commandRegistry() {
        return new CommandRegistry(authCommand(),
                dataCommand(),
                ehloCommand(),
                helloCommand(),
                helpCommand(),
                mailCommand(),
                noopCommand(),
                quitCommand(),
                receiptCommand(),
                resetCommand(),
                startTLSCommand(),
                verifyCommand(),
                expandCommand());
    }

    public Command authCommand() {
        return withTlsCheckWhenRequired(new AuthCommand());
    }

    public Command dataCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new DataCommand()));
    }

    public Command ehloCommand() {
        return new EhloCommand();
    }

    public Command helloCommand() {
        return withTlsCheckWhenRequired(new HelloCommand());
    }

    public Command helpCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new HelpCommand()));
    }

    public Command mailCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new MailCommand()));
    }

    public Command noopCommand() {
        return new NoopCommand();
    }

    public Command quitCommand() {
        return new QuitCommand();
    }

    public Command receiptCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new ReceiptCommand()));
    }

    public Command resetCommand() {
        return withTlsCheckWhenRequired(new ResetCommand());
    }

    public Command startTLSCommand() {
        return new StartTLSCommand();
    }

    public Command verifyCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new VerifyCommand()));
    }

    public Command expandCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new ExpandCommand()));
    }

    private Command withTlsCheckWhenRequired(Command c) {
        return new RequireTLSCommandWrapper(c);
    }

    private Command withAuthCheckWhenRequired(Command c) {
        return new RequireAuthCommandWrapper(c);
    }
}
