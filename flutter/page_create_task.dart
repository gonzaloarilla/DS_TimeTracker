import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/page_intervals.dart';
import 'package:time_tracker_flutter/page_report.dart';
import 'package:time_tracker_flutter/tree.dart' hide getTree;
import 'package:time_tracker_flutter/requests.dart';

class PageCreateTask extends StatefulWidget {
  late final int id;


  @override
  _CreateProjectTask createState() => _CreateProjectTask();
}

class _CreateProjectTask extends State<PageCreateTask> {
  //late int id;
  //late Future<Tree> futureTree;
  //late Tree tree;


  //@override
  //void initState() {
  //  super.initState();
  //  id = widget.id; // of PageActivities
  //  futureTree = getTree(id);
  //}

  GlobalKey formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text("New Task"),
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
      body: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Form(
          key: formKey,
          child: Column(
            children: <Widget>[
              TextFormField(
                decoration: InputDecoration(
                    labelText: "Name: "
                ),
              ),
              TextFormField(
                decoration: InputDecoration(
                    labelText: "Tags: "
                ),
              ),
              TextFormField(
                decoration: InputDecoration(
                    labelText: "Description: "
                ),
              ),

              OutlinedButton(
                child: Text('Create',
                  style: TextStyle(fontSize: 20),
                ),
                onPressed: (){
                  Text("Project Created!");
                },
                style: OutlinedButton.styleFrom(
                  side: BorderSide(
                    width: 2.0,
                    color: Colors.blue,
                    style: BorderStyle.solid,
                  ),

                ),
              )
            ],
          ),
        ),
      ),
    );
  }



}

