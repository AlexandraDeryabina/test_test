package ru.lanit.dto.response;

public class StatisticsDto {
    private Long personcount;
    private Long carcount;
    private Long uniquevendorcount;

    public StatisticsDto() {
    }

    public StatisticsDto(Long personcount, Long carcount, Long uniquevendorcount) {
        this.personcount = personcount;
        this.carcount = carcount;
        this.uniquevendorcount = uniquevendorcount;
    }

    public Long getPersoncount() {
        return personcount;
    }

    public void setPersoncount(Long personcount) {
        this.personcount = personcount;
    }

    public Long getCarcount() {
        return carcount;
    }

    public void setCarcount(Long carcount) {
        this.carcount = carcount;
    }

    public Long getUniquevendorcount() {
        return uniquevendorcount;
    }

    public void setUniquevendorcount(Long uniquevendorcount) {
        this.uniquevendorcount = uniquevendorcount;
    }
}
