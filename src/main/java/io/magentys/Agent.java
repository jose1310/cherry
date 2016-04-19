package io.magentys;

import io.magentys.exceptions.NotAvailableException;
import io.magentys.utils.Any;
import io.magentys.utils.Clazz;

import java.util.ArrayList;
import java.util.List;

import static io.magentys.utils.Any.any;
import static io.magentys.utils.Requires.requires;

public class Agent {

    private final Memory memory;
    private final List<Any> tools = new ArrayList<Any>();

    public Agent(final Memory memory) {
        this.memory = memory;
    }

    public static Agent withMemory(final Memory mem) {
        return new Agent(mem);
    }

    public <RESULT> RESULT performs(final Mission<RESULT> mission) {
        return mission.accomplishAs(this);
    }

    public Agent performAll(final Mission... missions) {
        requires(missions != null && missions.length > 0, "No Missions were passed");
        for (final Mission mission : missions) {
            mission.accomplishAs(this);
        }
        return this;
    }

    public <TOOL> Agent obtains(final TOOL... tools) {
        for (final TOOL tool : tools) {
            this.tools.add(any(tool));
        }
        return this;
    }

    public <TOOL> TOOL usingThe(final Class<TOOL> toolClass) {

        for (final Any tool : tools) {
            if (Clazz.isClassOrSubclass(toolClass, tool.get().getClass())) {
                return (TOOL) tool.get();
            }
        }

        throw new NotAvailableException("I don't know this skill: " + toolClass);
    }

    public <VALUE> void keepsInMind(final String key, final VALUE value) {
        this.memory.remember(key, value);
    }

    public <VALUE> VALUE recalls(final String key, final Class<VALUE> clazz) {
        return (VALUE) memory.recall(key, clazz);
    }

    public Agent and(final Mission mission) {
        performAll(mission);
        return this;
    }

    public Agent andHe(final Mission... missions) {
        return performAll(missions);
    }

    public Agent andShe(final Mission... missions) {
        return performAll(missions);
    }


}
