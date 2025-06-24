package sn.Fama_Taha.repository;

import java.util.List;

import sn.Fama_Taha.entity.Membre;

public interface IMembreRepository {
    List<Membre> findAll();
    Membre findById(String idMembre);
    Membre save(Membre membre);
    void delete(Membre membre);
    Membre update(Membre membre);
}
