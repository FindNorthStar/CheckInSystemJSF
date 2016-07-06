package edu.bupt.checkinsystem;

import org.ocpsoft.logging.Logger.Level;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Log;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import javax.servlet.ServletContext;

@RewriteConfiguration
public class RewriteManager extends HttpConfigurationProvider
{
    @Override
    public Configuration getConfiguration(ServletContext context)
    {
        return ConfigurationBuilder.begin()
                .addRule()
                .perform(Log.message(Level.INFO, "Rewrite is active."))

                .addRule(Join.path("/{param}").to("/faces/{param}.xhtml"))
                .addRule(Join.path("/{param1}/{param2}").to("/faces/{param1}/{param2}.xhtml"))
                ;
    }

    @Override
    public int priority()
    {
        return 0;
    }
}