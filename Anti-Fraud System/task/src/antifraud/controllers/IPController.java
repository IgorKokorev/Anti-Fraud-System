package antifraud.controllers;

import antifraud.DBtables.SuspiciousIpDB;
import antifraud.DTORequests.SuspiciousIpToAdd;
import antifraud.DTOResponses.Status;
import antifraud.repository.SuspiciousIpDBRepository;
import antifraud.config.CheckData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class IPController {
    @Autowired
    SuspiciousIpDBRepository ipRepository;

    // Add new suspicious IP
    @PostMapping(value = "/api/antifraud/suspicious-ip")
    public SuspiciousIpDB newSuspiciousIP(@RequestBody SuspiciousIpToAdd iPreceived) {

        if (!CheckData.isIPcorrect(iPreceived.getIp())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect IP format");
        }

        List<SuspiciousIpDB> suspiciousIps = ipRepository.findByIp(iPreceived.getIp());

        if (suspiciousIps.size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "The IP was already registered");

        SuspiciousIpDB suspiciousIpDB = new SuspiciousIpDB();
        suspiciousIpDB.setIp(iPreceived.getIp());

        suspiciousIpDB = ipRepository.save(suspiciousIpDB);

        return suspiciousIpDB;
    }

    // Delete suspicious IP from DB
    @DeleteMapping(value = "/api/antifraud/suspicious-ip/{ip}")
    public Status deleteIP(@PathVariable String ip) {
        if (!CheckData.isIPcorrect(ip)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrect IP format");
        }

        List<SuspiciousIpDB> suspiciousIps = ipRepository.findByIp(ip);

        if (suspiciousIps.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The IP was not found in DB");

        suspiciousIps.forEach(suspiciousIp -> ipRepository.deleteById(suspiciousIp.getId()));
        return new Status("IP " + ip + " successfully removed!");
    }

    // Get list of suspicious IP address sorted by ID in ascending order
    @GetMapping(value = "/api/antifraud/suspicious-ip")
    public List<SuspiciousIpDB> getListOfSuspiciousIps() {
        return ipRepository.findAllByOrderById();
    }



}
