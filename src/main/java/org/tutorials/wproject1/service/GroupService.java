package org.tutorials.wproject1.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.tutorials.wproject1.model.Group;
import org.tutorials.wproject1.model.GroupAttr;
import org.tutorials.wproject1.model.Member;
import org.tutorials.wproject1.repository.GroupRepository;

@Service
public class GroupService implements IGroupService {

    private AtomicLong sequence = new AtomicLong(0);
    private static List<Group> groups=new ArrayList<>();

    //@Autowired
    //private GroupRepository repository;

    @Override
    public List<Group> findAll() {

      return groups;
    }

    @Override
    public Optional<Group> findGroup(Long gid) {

        return groups.stream().filter(g->Objects.equals(g.getGid(), gid)).findAny();
    }

    @Override
    public Group findGroupAttr(Long gid) {

       return groups.stream().filter(g->Objects.equals(g.getGid(), gid)).findAny().orElse(null);

    }

    @Override
    public Optional<List<Group>> findMemberById(String memberId) {
        List<Group>gs=groups.stream().filter(g->g.getMembers().containsKey(memberId)).collect(Collectors.toList());
        return Optional.ofNullable(gs);
    }

    @Override
    public Group createGroup(Map<String, String> groupAttr) {
        Long key=sequence.incrementAndGet();
        Group group=new Group(key, groupAttr);
        groups.add(group);
        return group;
    }

    @Override
    public void deleteGroup(Group group)
    {

        groups.remove(group);
    }

    @Override
    public List<Group> deleteMember(String memberId) {
        for (Group g1 : groups) {
            //if (g1.getMembers().keySet().contains(memberId)) {
                groups.remove(g1);
                //g1.getMembers().keySet().remove(memberId);
                //groups.add(g1);
            //}
        }
        return groups;
       //existingGroup.getMembers().remove(memberId);
    }

    @Override
    public Optional<Group> updateGroupAttribute(Long gid, Map<String, String>attrs) {

        Optional<Group>foundGroup=this.findGroup(gid);

        foundGroup.ifPresent(g->{
            g.setAttributes(attrs);

        });
        return foundGroup;

    }

    @Override
    public Optional<Group> updateGroupMember(Long gid, Member memberIn) {
        Optional<Group>foundGroup=this.findGroup(gid);

        foundGroup.ifPresent(g->{
            g.setMembers(memberIn);
        });
        return foundGroup;

    }

}
