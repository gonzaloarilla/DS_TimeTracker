import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/page_intervals.dart';
import 'package:time_tracker_flutter/page_report.dart';
import 'package:time_tracker_flutter/requests.dart';
import 'package:time_tracker_flutter/tree.dart' as Tree hide getTree;
import 'dart:core';
import 'dart:convert';

class PageProjectDetails extends StatefulWidget {
  late final int id;
  PageProjectDetails(this.id);


  @override
  _PageProjectState createState() => _PageProjectState();
}

class _PageProjectState extends State<PageProjectDetails> {
  late int id;
  // late Future<Tree> futureTree;
  // late Tree tree;

  void initState() {
    super.initState();
    // getTree(1);
    id = widget.id; // of PageActivities
    // futureTree = getTree(id);
    // nameCtrl, tags = TextEditingController();
    // parentId = widget.parentId;
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Project name"),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {
                Navigator.popUntil(context, ModalRoute.withName('/'));
              }
          ),
          IconButton(
              onPressed: () {
              }, icon: Icon(Icons.search))
        ],
      ),
      body: Container(
        margin: EdgeInsets.only(left: 40),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Row(
                children: [
                  Container(
                      width: 120,
                      child: Text('Start Date: ',
                        style: new TextStyle(
                          fontSize: 20.0,
                          fontWeight: FontWeight.bold,
                        ),
                      )
                  ),
                  Text("getStartDate"),
                ],
              ),
              Row(
                children: [
                  Container(
                      width: 120,
                      child: Text('Final Date: ',
                        style: new TextStyle(
                          fontSize: 20.0,
                          fontWeight: FontWeight.bold,
                        ),
                      )
                  ),
                  Text("getFinalDate"),
                ],
              ),
              Row(
                children: [
                  Container(
                      width: 120,
                      child: Text('Duration: ',
                        style: new TextStyle(
                          fontSize: 20.0,
                          fontWeight: FontWeight.bold,
                        ),
                      )
                  ),
                  Text("getDuration"),
                ],
              ),
              Row(
                children: [
                  Container(
                      width: 120,
                      child: Text('Tags: ',
                        style: new TextStyle(
                          fontSize: 20.0,
                          fontWeight: FontWeight.bold,
                        ),
                      )
                  ),
                  Text("getTags"),
                ],
              ),
              Row(
                children: [
                  Container(
                      width: 120,
                      child: Text('Description: ',
                        style: new TextStyle(
                          fontSize: 20.0,
                          fontWeight: FontWeight.bold,
                        ),
                    )
                  ),
                  Text("getDescription"),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
//
void _test(id) {
  dynamic d = getTree(id);
}

void _printThings(id) {
  print("Current ID " + id.toString());
}

void _getInfoTree(id) {
  print("ENTRO");
  var a = getTree(id).runtimeType;
  // Map<String,
  // Project.fromJson(a);
  print(a);

  // var stringed = a.toString();
  var toJson = jsonEncode(getTree(id));
  // var d = jsonDecode(toJson);
  // print(d);
  // Map<String, dynamic> json = toJson;
  print(toJson);

  //
  // if (toJson.contains('nodeList')) {
  //   for (Map<String, dynamic> jsonChild in toJson['nodeList']) {
  //     print(jsonChild['name']);
  // }
  // print(toJson['name']);
  // print(toJson);
  // print(a);
}