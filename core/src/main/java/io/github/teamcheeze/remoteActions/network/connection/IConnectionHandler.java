package io.github.teamcheeze.remoteActions.network.connection;

import io.github.teamcheeze.remoteActions.client.Client;
import io.github.teamcheeze.remoteActions.network.client.ClientAddress;
import io.github.teamcheeze.remoteActions.network.server.ServerAddress;
import io.github.teamcheeze.remoteActions.server.Server;
import io.github.teamcheeze.remoteActions.util.ExceptionCodes;
import org.jetbrains.annotations.Nullable;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class IConnectionHandler {
    private final static ArrayList<Client> registeredClients = new ArrayList<>();
    private final static ArrayList<Server> registeredServers = new ArrayList<>();
    public static void registerClient(Client client) {
        registeredClients.add(client);
    }
    public static void registerServer(Server server) {
        registeredServers.add(server);
    }

    /**
     *
     * @param filter Filter
     * @return The address of the given client
     */
    @Nullable
    private static Client getClient(Function<Client, Boolean> filter) {
        List<Client> filtered = registeredClients.stream().filter(filter::apply).toList();
        if (filtered.size() != 1) {
            if (filtered.size() > 1) {
                System.exit(ExceptionCodes.DUPLICATE_CLIENT_CODE.getId());
            }
            return null;
        }
        return filtered.get(0);
    }

    @Nullable
    private static Server getServer(Function<Server, Boolean> filter) {
        List<Server> filtered = registeredServers.stream().filter(filter::apply).toList();
        if (filtered.size() != 1) {
            if (filtered.size() > 1) {
                System.exit(ExceptionCodes.DUPLICATE_CLIENT_CODE.getId());
            }
            return null;
        }
        return filtered.get(0);
    }
    @Nullable
    public static Client getClient(ClientAddress clientAddress) {
        return getClient((client)->client.getAddress() == clientAddress);
    }

    @Nullable
    public static Client getClient(Inet4Address clientAddress) {
        return getClient((client -> client.getAddress().getIp().getHostAddress().equals(clientAddress.getHostAddress())));
    }

    @Nullable
    public static Client getClient(UUID clientId) {
        return getClient((client)->client.getId() == clientId);
    }

    @Nullable
    public static Server getServer(ServerAddress serverAddress) {
        return getServer((server)->server.getAddress() == serverAddress);
    }

    @Nullable
    public static Server getServer(UUID serverId) {
        return getServer((server)->server.getId() == serverId);
    }
}