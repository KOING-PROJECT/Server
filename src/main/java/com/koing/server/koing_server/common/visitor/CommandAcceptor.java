package com.koing.server.koing_server.common.visitor;

public interface CommandAcceptor {
    void accept(CommandVisitor visitor);
}
