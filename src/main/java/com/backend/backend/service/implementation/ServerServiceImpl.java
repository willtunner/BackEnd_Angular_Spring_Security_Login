package com.backend.backend.service.implementation;

import com.backend.backend.models.Server;
import com.backend.backend.repositories.ServerRepo;
import com.backend.backend.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.backend.backend.enuns.Status.SERVER_DOWN;
import static com.backend.backend.enuns.Status.SERVER_UP;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    // Injeta repositório do server
    private final ServerRepo serverRepo;// @RequiredArgsConstructor - escreve dessa forme em vez de colocar o Autowired

    @Override
    public Server create(Server server) {
        log.info( "Salvando novo server: {}", server.getName() );
        server.setImageUrl(setServerImageUrl()); // set image up or down
        return serverRepo.save(server);// salva no banco o server
    }


    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info( "Ping server ip: ", ipAddress );
        Server server = serverRepo.findByIpAddress(ipAddress); // procura server por ip
        InetAddress address = InetAddress.getByName(ipAddress); //  fornece métodos para obter o IP de qualquer nome de host, por exemplo www.google.com, www.facebook.com, etc.
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN); // Seta o status do servidor
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info( "Listando todos os servers!" );
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();// cria paginação
    }

    @Override
    public Server get(Long id) {
        log.info( "Procura server por id ", id );
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info( "Atualzando server: {}", server.getName() );
        return serverRepo.save(server);// salva no banco o server
    }

    @Override
    public Boolean delete(Long id) {
        log.info( "Deleta server por id ", id );
        serverRepo.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] imageNames = { "server1.png","server2.png","server3.png","server4.png" };// 4 images server up
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString(); // pega o caminho dinamicamente e randomicamente das imagens
    }

}
