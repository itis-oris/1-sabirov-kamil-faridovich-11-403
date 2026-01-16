package ru.itis.dis403.auction.service;

import ru.itis.dis403.auction.dao.LicensePlateDao;
import ru.itis.dis403.auction.model.LicensePlate;
import ru.itis.dis403.auction.util.ValidateUtil;

import java.util.List;

public class LicensePlateService {
    private LicensePlateDao plateDao;

    public LicensePlateService(LicensePlateDao plateDao) {
        this.plateDao = plateDao;
    }

    public List<LicensePlate> getAllActivePlates() {
        return plateDao.findAllActive();
    }

    public LicensePlate getPlateById(int id) {
        return plateDao.findById(id);
    }

    public void createPlate(LicensePlate plate) {
        ValidateUtil.validateText(plate.getDescription());
        if (plate.getCurrentPrice() == null) {
            plate.setCurrentPrice(plate.getStartingPrice());
        }
        if (plateDao.existsActiveByNumberAndRegion(plate.getNumber(), plate.getRegion())) {
            throw new IllegalArgumentException("Такой номер уже существует в активных аукционах: " + plate.getNumber() + " " + plate.getRegion());
        }
        plateDao.save(plate);
    }

    public void deletePlate(LicensePlate plate) {
        plateDao.delete(plate.getId());
    }

    public void updatePlate(LicensePlate plate) {
        ValidateUtil.validateText(plate.getDescription());
        plateDao.update(plate);
    }

    public List<LicensePlate> getUserLots(Integer userId) {
        return plateDao.findByCreatorId(userId);
    }
}