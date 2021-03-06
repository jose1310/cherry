package io.magentys;

import java.util.HashSet;
import java.util.Set;

import static io.magentys.CoreMemory.coreMemory;


/**
 * Agent Provider creates agents as a Builder
 */
public class AgentProvider {

    private AgentProvider(){
        anAgent = agent();
    }

    private AgentProvider(Agent agent){
        anAgent = agent;
    }

    private Agent anAgent;

    /**
     * Provides a vanilla agent with CoreMemory
     * @return an empty agent
     */
    public static Agent agent() {
        return new Agent(coreMemory());
    }

    /**
     * Create a new agent with a type of memory
     * @param memory
     * @return
     */
    public static Agent agentWithMemory(Memory memory){
        return new Agent(memory);
    }

    /**
     * The starting point for creating agents
     * @return an AgentProvider
     */
    public static AgentProvider provideAgent() {
        return new AgentProvider();
    }

    public AgentProvider called(String name){
       anAgent = anAgent.clone();
       anAgent.setName(name);
       return this;
    }

    /**
     * Create a new agent with the memory provided
     * @param memory
     * @return a new agent
     */
    public AgentProvider withMemory(Memory memory){
        anAgent = anAgent.clone();
        anAgent.setMemory(memory);
        return this;
    }

    /**
     * Create a new agent with the tools provided
     * @param tools
     * @return a new agent
     */
    public AgentProvider withTools(Object... tools){
        anAgent = anAgent.clone().obtains(tools);
        return this;
    }

    public AgentProvider withNarrators(Narrator... narrators){
        Set<Narrator> narratorSet = new HashSet<Narrator>();
        for(Narrator narrator : narrators){
            narratorSet.add(narrator);
        }
        anAgent = anAgent.clone().setNarrators(narratorSet);
        return this;
    }

    /**
     * Return the agent
     * @return the built agent
     */
    public Agent get(){
        return anAgent;
    }


}
