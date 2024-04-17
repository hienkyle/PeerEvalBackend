package edu.tcu.cs.peerevalbackend.war;

import edu.tcu.cs.peerevalbackend.system.Result;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import edu.tcu.cs.peerevalbackend.war.converter.WarDtoToWarConverter;
import edu.tcu.cs.peerevalbackend.war.converter.WarToWarDtoConverter;
import edu.tcu.cs.peerevalbackend.war.dto.WarDto;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peereval/war")
public class WarController {
    private final WarService warService;
    private final WarToWarDtoConverter warToWarDtoConverter;
    private final WarDtoToWarConverter warDtoToWarConverter;

    public WarController(WarService warService, WarToWarDtoConverter warToWarDtoConverter, WarDtoToWarConverter warDtoToWarConverter) {
        this.warService = warService;
        this.warToWarDtoConverter = warToWarDtoConverter;
        this.warDtoToWarConverter = warDtoToWarConverter;
    }

    @GetMapping("/{warId}")
    public Result findWarById(@PathVariable Integer warId){
        WeeklyActivityReport foundWar = this.warService.findById(warId);
        WarDto foundDto = this.warToWarDtoConverter.convert(foundWar);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", foundWar);
    }
    @GetMapping()
    public Result findAllWars(){
        List<WeeklyActivityReport> foundWars = this.warService.findAll();
        List<WarDto> foundDtos =  foundWars.stream().map(this.warToWarDtoConverter::convert).collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", foundDtos);
    }
    @PostMapping("{studentId}")
    public Result addWar(@PathVariable Integer studentId,@Valid @RequestBody WarDto newDto){
        WeeklyActivityReport newWar = warDtoToWarConverter.convert(newDto);
        WeeklyActivityReport savedWar = this.warService.save(studentId, newWar);
        WarDto savedDto = this.warToWarDtoConverter.convert(savedWar);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedDto);
    }
    @DeleteMapping("/{warId}")
    public Result deleteWar(@PathVariable Integer warId){
        this.warService.delete(warId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
    @PutMapping("/{warId}")
    public Result updateWar(@PathVariable Integer warId, @Valid @RequestBody WarDto warDto){
        WeeklyActivityReport update =  warDtoToWarConverter.convert(warDto);
        WeeklyActivityReport updatedWar = this.warService.update(warId, update);
        WarDto updatedDto =  this.warToWarDtoConverter.convert(updatedWar);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedDto);
    }

}
