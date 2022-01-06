import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/page_intervals.dart';
import 'package:time_tracker_flutter/page_report.dart';
import 'package:time_tracker_flutter/tree.dart' hide getTree;
import 'package:time_tracker_flutter/requests.dart';
import 'dart:core';
import 'dart:convert';

class PageCreateTask extends StatefulWidget {
  late final int id;
  // late TextEditingController taskController;
  late int parentId;
  PageCreateTask(this.parentId);

  @override
  _CreateTaskState createState() => _CreateTaskState();


}

class _CreateTaskState extends State<PageCreateTask> {
  GlobalKey formKey = GlobalKey<FormState>();
  late TextEditingController nameCtrl = TextEditingController();
  late TextEditingController tagsCtrl = TextEditingController();
  late TextEditingController descriptionCtrl = TextEditingController();
  late int parentId;

  @override
  void initState() {
    super.initState();
    // nameCtrl, tags = TextEditingController();
    parentId = widget.parentId;
  }

  @override
  void dispose() {
    nameCtrl.dispose();
    tagsCtrl.dispose();
    descriptionCtrl.dispose();
    super.dispose();
  }

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
                controller: nameCtrl,
                decoration: InputDecoration(
                    labelText: "Name: "
                ),
              ),
              TextFormField(
                controller: tagsCtrl,
                decoration: InputDecoration(
                    labelText: "Tags: "
                ),
              ),
              TextFormField(
                controller: descriptionCtrl,
                decoration: InputDecoration(
                    labelText: "Description: "
                ),
              ),

              OutlinedButton(
                child: Text('Create',
                  style: TextStyle(fontSize: 20),
                ),
                onPressed: (){
                  // _dialogForTest(tagsCtrl.text);
                  _makeEncoded(nameCtrl.text, descriptionCtrl.text, _splitTags(tagsCtrl.text));
                  int count = 0;
                  Navigator.of(context).popUntil((_) => count++ >= 1);
                  
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

  // per testejar els resultats dels controladors
  void _dialogForTest(String txt) async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Controller Value'),
          content: SingleChildScrollView(
            child: ListBody(
              children: <Widget>[
                Text(txt),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text(
                'ACCEPT',
                style: TextStyle(fontSize: 20),
              ),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  // per separar els tags
  List<String> _splitTags(String tags) {
    var splitted = tags.split(", ");
    return splitted;
  }

  void _makeEncoded(String name, String description, List<String> tags) {
    // dynamic --> pot ser qualsevol tipus
    Map<String, dynamic> toJson = {'name': name, 'description': description, 'tags': tags};
    // print(toJson);
    var jsoned = json.encode(toJson);
    // print(jsoned);

    addNode(parentId, 0, name, description, tags);
  }
}

