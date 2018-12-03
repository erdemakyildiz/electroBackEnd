package com.electro.service;

import com.electro.exception.HeadlineNotFoundException;
import com.electro.models.Headline;
import com.electro.repository.HeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HeadlineService {

    @Autowired
    private HeadlineRepository headlineRepository;


    public Headline findOrCreateHeadline(String name) {
        Headline headline = headlineRepository.findFirstByNameEquals(name);

        if (headline == null){
            headline = new Headline();
            headline.setName(name);
            headline.setActive(true);
            headline.setCreateDate(new Date());
            headline.setStreaks(new ArrayList<>());

            headlineRepository.save(headline);
        }

        return headline;
    }

    public Headline findHeadline(String name) throws HeadlineNotFoundException {
        Headline headline = headlineRepository.findFirstByNameEquals(name);

        if (headline == null)
            throw new HeadlineNotFoundException(String.format("kategori bulunamadı isim:%s", name));

        return headline;
    }

    public List<Headline> findHeadlines(){
        List<Headline> all = headlineRepository.findAll();

        return all;
    }

    public void activeHeadline(Headline headline) throws HeadlineNotFoundException {
        final Headline headlineFromDB = getHeadlineFromDB(headline.getId());
        headlineFromDB.setActive(true);

        headlineRepository.save(headline);
    }

    public void passiveHeadline(Headline headline) throws HeadlineNotFoundException {
        final Headline headlineFromDB = getHeadlineFromDB(headline.getId());
        headlineFromDB.setActive(false);

        headlineRepository.save(headline);
    }

    private Headline getHeadlineFromDB(long id) throws HeadlineNotFoundException {
        final Optional<Headline> optional = headlineRepository.findById(id);
        final Headline headline = optional.orElse(null);

        if (headline == null)
            throw new HeadlineNotFoundException(String.format("kategori bulunamadı id:%s", id));

        return headline;
    }

}
